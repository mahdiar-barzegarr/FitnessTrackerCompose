package ir.company.fitnessapp.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.content.edit

data class HistoryItem(
    val activityName: String,
    val date: String,
    val time: String,
    val duration: String,
    val caloriesBurned: String
)

private const val PREFS_NAME = "history_prefs"
private const val KEY_HISTORY = "history_list"

fun saveHistoryItem(context: Context, item: HistoryItem) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val list = loadHistoryList(context).toMutableList()
    list.add(item)

    val jsonArray = JSONArray()
    list.forEach {
        val obj = JSONObject()
        obj.put("activityName", it.activityName)
        obj.put("date", it.date)
        obj.put("time", it.time)
        obj.put("duration", it.duration)
        obj.put("caloriesBurned", it.caloriesBurned)
        jsonArray.put(obj)
    }

    prefs.edit { putString(KEY_HISTORY, jsonArray.toString()) }
}


fun loadHistoryList(context: Context): List<HistoryItem> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(KEY_HISTORY, null) ?: return emptyList()

    val jsonArray = JSONArray(json)
    val list = mutableListOf<HistoryItem>()

    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        list.add(
            HistoryItem(
                activityName = obj.getString("activityName"),
                date = obj.getString("date"),
                time = obj.getString("time"),
                duration =obj.getString("duration"),
                caloriesBurned = obj.getString("caloriesBurned")
            )
        )
    }
    return list
}
