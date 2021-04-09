package com.example.meditime

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_medicine_time.*

/*********************************
 * 화면 #3-2-2 시간설정
 * 몇시에 알람울 울릴지에 대한 정보 목록이 있는 화면
 *********************************/

class AddMedicineTimeActivity : AppCompatActivity() {

    val ADD_MEDICINE_TIME_SET = 201
    lateinit var alarmAdapter:AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine_time)
        recyclerViewInit()
        listenerInit()
    }

    private fun recyclerViewInit() {
        val alarmList = getDummyAlarmItems()
        alarmAdapter = AlarmAdapter(alarmList)
        rv_addmeditime_container.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_addmeditime_container.adapter = alarmAdapter
    }

    private fun getDummyAlarmItems(): ArrayList<AlarmInfo> {
        val dummy_list = ArrayList<AlarmInfo>()
        dummy_list.add(AlarmInfo(
            alarm_hour = 8,
            alarm_min = 0,
            medicine_count = 1.0
        ))
        dummy_list.add(AlarmInfo(
            alarm_hour = 9,
            alarm_min = 30,
            medicine_count = 1.5
        ))
        return dummy_list
    }

    private fun listenerInit() {
        btn_addmeditime_ok.setOnClickListener {
            // 완료 버튼 클릭 시
            // Todo : 데이터베이스에 모든 값 저장하기
            finishAffinity()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_addmeditime_add.setOnClickListener {
            // 추가 버튼 클릭 시
            val intent = Intent(this, AddMedicineTimeSetActivity::class.java)
            startActivityForResult(intent, ADD_MEDICINE_TIME_SET)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_MEDICINE_TIME_SET && resultCode == Activity.RESULT_OK){
            // AddMedicineTimeSetActivity 에서 call back
            val hour = data!!.getIntExtra("hour", -1)
            val min = data!!.getIntExtra("min", -1)
            val count = data!!.getDoubleExtra("count", -1.0)
            if(hour!=-1 && min!=-1 && count!=-1.0) {
                val newAlarmInfo = AlarmInfo(
                    alarm_hour = hour,
                    alarm_min = min,
                    medicine_count = count
                )
                alarmAdapter.addItem(newAlarmInfo)
                alarmAdapter.notifyAdapter()
            }
        }
    }
}