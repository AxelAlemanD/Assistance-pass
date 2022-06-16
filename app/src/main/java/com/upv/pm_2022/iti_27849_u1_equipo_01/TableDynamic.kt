package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.content.Context
import android.util.Log
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS
import android.widget.TableLayout
import android.widget.TextView
import android.view.Gravity
import android.widget.TableRow
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES.getStudentAssistances
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance
import java.util.*
import kotlin.math.log

class TableDynamic(private val tableLayout: TableLayout, private val context: Context) {
    private lateinit var ids: MutableList<String>
    private  var studentAssistances: MutableList<Assistance> = mutableListOf()
    private lateinit var absences: MutableList<String>
    private lateinit var header: Array<String>
    private var assistance: MutableList<Assistance> = mutableListOf()
    private var data: ArrayList<Array<String>>? = null
    private var tableRow: TableRow? = null
    private var txtCell: TextView? = null
    private var indexC = 0
    private var indexR = 0
    private val dates: MutableList<String> = mutableListOf()
    private val auxiliarDates: MutableList<String> = mutableListOf()
    val allAssistances = ArrayList<Array<String>>()
    private val days = ArrayList<Array<String>>()

    fun addAssistance(assistance: MutableList<Assistance>){
        this.assistance = assistance
    }
    fun addHeader(header: Array<String>,idNumbers:MutableList<String>) {
        this.header = header
        this.ids = idNumbers
        createHeader()
    }

    fun addData(data: ArrayList<Array<String>>?) {
        this.data = data
        createDataTable()
    }

    private fun newRow() {
        tableRow = TableRow(context)
    }

    private fun newCell() {
        txtCell = TextView(context)
        txtCell!!.gravity = Gravity.CENTER
        txtCell!!.textSize = 20f
    }

    private fun createHeader() {
        indexC = 0
        newRow()
        while (indexC < header.size) {
            newCell()
            txtCell!!.text = header[indexC++]
            tableRow!!.addView(txtCell, newTableRowParams())
        }
        if (this.assistance.isNotEmpty()) {
            for (date in assistance) {
                dates.add(date.date)
            }
            for(date in dates.distinct()){
                newCell()
                txtCell!!.text = date
                tableRow!!.addView(txtCell, newTableRowParams())
            }
        }

        tableLayout.addView(tableRow)

    }

    private fun createDataTable() {
        var info: String
        var info2: String
        indexR = 1
        getAssistancesNumber()
        getAssistanceDays()
        while (indexR <= data!!.size) {
            newRow()
            indexC = 0
            while (indexC <= header.size-3) {
                newCell()
                val columns = data!![indexR - 1]
                info = if (indexC < columns.size) columns[indexC] else ""
                txtCell!!.text = info
                tableRow!!.addView(txtCell, newTableRowParams())
                indexC++
                Log.e("index dentro",indexC.toString())
            }

            Log.e("index fuera",indexC.toString())
            indexC = 0
            while(indexC <= (4)){
                Log.e("index dentro",indexC.toString())
                newCell()
                val columns = allAssistances!![indexR-1]
                info2 = if (indexC < columns.size) columns[indexC] else ""
                txtCell!!.text = info2
                tableRow!!.addView(txtCell, newTableRowParams())
                indexC++
            }
            while(indexC <= header.size){
                newCell()
                val columns = days!![indexR-1]
                info2 = if (indexC < columns.size) columns[indexC] else ""
                txtCell!!.text = info2
                tableRow!!.addView(txtCell, newTableRowParams())
                indexC++
            }
            tableLayout.addView(tableRow)

            indexR++
        }
    }

    fun addItem(item: Array<String>) {
        var info: String
        data!!.add(item)
        indexC = 0
        newRow()
        while (indexC < header.size) {
            newCell()
            info = if (indexC < item.size) item[indexC++] else ""
            txtCell!!.text = info
            tableRow!!.addView(txtCell, newTableRowParams())
        }
        tableLayout.addView(tableRow, data!!.size)
    }

    private fun newTableRowParams(): TableRow.LayoutParams {
        val params = TableRow.LayoutParams()
        params.setMargins(15, 10, 15, 10)
        params.weight = 1f
        return params
    }

    fun clear(){
        this.header = emptyArray()
        if(this.assistance.isNotEmpty()){
            this.assistance.clear()
        }
        this.data = ArrayList<Array<String>>()
        tableLayout.removeAllViews()
    }

    fun getAssistancesNumber(){
        var absenceCounter: Int
        var totalCounter: Int
        for (j in ids){
            absenceCounter = 0
            totalCounter = 0
            for (i in assistance){
                if (i.student_id.equals(j) or i.student_id.equals(j.toInt())){
                    if (i.is_late.equals(1) or equals('1')){
                        absenceCounter++
                    }
                    totalCounter++
                }
            }
            allAssistances.add(arrayOf(absenceCounter.toString(),(totalCounter-absenceCounter).toString()))
        }
    }

    fun getAssistanceDays(){
        var aux :String
        auxiliarDates.clear()
        for (j in ids){
            for (i in assistance){
                if (i.student_id.equals(j) or i.student_id.equals(j.toInt())){
                     auxiliarDates.add("A")
                }else
                    auxiliarDates.add("F")
            }
            days.add(auxiliarDates.toTypedArray())
        }
    }
}
