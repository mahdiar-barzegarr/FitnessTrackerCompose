package ir.company.fitnessapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    var activityTitle by mutableStateOf("")
    var icon by mutableIntStateOf(R.drawable.run_)

}