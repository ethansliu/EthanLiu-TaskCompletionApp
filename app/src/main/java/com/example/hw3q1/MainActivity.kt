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
    var tasks by remember { mutableStateOf("") }
    var taskListing = remember { mutableStateListOf<Pair<String, Boolean>>() }

    Column{
        TextField(
            value = tasks,
            onValueChange = {tasks = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Enter your task")}
        )

        Button(
            onClick = {
                if(tasks.isNotEmpty()){
                    taskListing.add(Pair(tasks,false))
                    tasks=""
                }
            }
        ) {
            Text("Add Task")
        }
        taskList(taskListing = taskListing)

        Button(
            onClick = {
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