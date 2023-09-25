package com.example.colanportfolio.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.colanportfolio.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

fun Context.snackBar(message: CharSequence, view: View) {
    val snackBar: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
    snackBar.view.setBackgroundColor(Color.parseColor("#FCBA13"))
    val textView = snackBar.view.findViewById(R.id.snackbar_text) as TextView
    textView.setTextColor(Color.parseColor("#000000"))
    snackBar.show()
}

fun navigateWithClearTop(context: Context, clazz: Class<*>, mExtras: Bundle?) {
    val intent = Intent(context, clazz)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    if (mExtras != null) {
        intent.putExtras(mExtras)
    }
    context.startActivity(intent)
    ActivityCompat.finishAffinity(context as Activity)
}

fun dateFormatDot(str: String?): String? {
    return if (str != null) {
        val parser = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatter.format(parser.parse(str))
        date
    } else {
        ""
    }
}

fun dateFormat(str: String?): String? {
    return if (str != null) {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = formatter.format(parser.parse(str))
        date
    } else {
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun currentDate() : String{
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = sdf.format(Date())
    return currentDate
}

fun currentDateFormat() : String{
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    return sdf.format(Date())
}

fun myQuoteCurrentDateFormat() : String{
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(Date())
}

fun stringToDateWiseServiceRequest(str: String?): String? {
    return if (!str.equals("")) {
        val parser = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = formatter.format(parser.parse(str))
        date
    } else {
        ""
    }
}

fun stringToDateWiseUpcoming(str: String?): String? {
    return if (!str.equals("")) {
        val parser = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = formatter.format(parser.parse(str))
        date
    } else {
        ""
    }
}

fun shipmentDetailDateFormat(str: String?): String? {
    return if (!str.equals("")) {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa", Locale.getDefault())
        val date = formatter.format(parser.parse(str))
        date
    } else {
        ""
    }
}

fun getCalculatedDate(date: String, dateFormat: String, days: Int): String {
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat(dateFormat)
    if (date.isNotEmpty()) {
        cal.time = s.parse(date)
    }
    cal.add(Calendar.DAY_OF_YEAR, days)
    return s.format(Date(cal.timeInMillis))
}

fun isValidGSTNo(str: String?): Boolean {
    val regex = ("^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
            + "Z[0-9A-Z]{1}$")
    val p: Pattern = Pattern.compile(regex)
    if (str == null) {
        return false
    }
    val m: Matcher = p.matcher(str)
    return m.matches()
}

fun getTime() : String {
    val dateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
    return dateFormat.format(Date())
}

fun rescheduleDateTime(str: String?): String? {
    return if (!str.equals("")) {
        val parser = SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = formatter.format(parser.parse(str))
        date
    } else {
        ""
    }
}
