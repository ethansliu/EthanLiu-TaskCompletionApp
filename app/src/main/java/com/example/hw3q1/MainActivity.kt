package com.example.hw3q1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.hw3q1.ui.theme.HW3Q1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HW3Q1Theme {
                TaskApp()
            }
        }
    }
}

@Composable
fun TaskApp(){
    //variable for each task
    var tasks by remember { mutableStateOf("") }
    //create list and couple it with string and boolean , true = completed. false = not completed
    var taskListing = remember { mutableStateListOf<Pair<String, Boolean>>() }

    Column{
        TextField(
            value = tasks,
            onValueChange = {tasks = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Enter your task")}
        )

        //if text field is not empty, add it to the list (task Listing)
        Button(
            onClick = {
                if(tasks.isNotEmpty()){
                    taskListing.add(Pair(tasks,false))
                    //clear the Text on textfield for next task
                    tasks=""
                }
            }
        ) {
            Text("Add Task")
        }
        taskList(taskListing = taskListing)

        Button(
            onClick = {
                //safely copy all the not completed tasks to temporary list and remove the original and copy not completed tasks back to original
                val temp = mutableListOf<Pair<String, Boolean>>()
                for (task in taskListing){
                    if(task.second == false){
                        temp.add(task)
                    }
                }
                taskListing.clear()
                taskListing.addAll(temp)
            }
        ){Text("Clear Completed Task")}
    }
}

@Composable
fun taskList(taskListing: MutableList<Pair<String, Boolean>>) {
    Column {
        for (task in taskListing) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                //if check box is checked, update the boolean so system will know that specific task is completed.
                Checkbox(
                    checked = task.second,
                    onCheckedChange = { isChecked ->
                        val updatedTask = task.copy(second = isChecked)
                        taskListing[taskListing.indexOf(task)] = updatedTask
                    }
                )


                Text(text = task.first)
            }
        }
    }
}