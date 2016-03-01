package com.example.datatimedialog;

import java.util.Calendar;

import android.animation.TimeAnimator.TimeListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button button1, button2;
	private int hourOfDay, minute;
	private int year, monthOfYear, dayOfMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		Calendar cal = Calendar.getInstance();
		hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		year = cal.get(Calendar.YEAR);
		monthOfYear = cal.get(Calendar.MONTH);
		dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			TimePickerDialog timePickerDialog = new TimePickerDialog(this, new MyTimePickerDialog(), hourOfDay, minute,
					true);
			timePickerDialog.show();

		}
		else if (v.getId() == R.id.button2) {
			DatePickerDialog datePickerDialog = new DatePickerDialog(this, new MyDatePickerDialog(), year, monthOfYear,
					dayOfMonth);
			datePickerDialog.show();
		}
	}

	public class MyTimePickerDialog implements TimePickerDialog.OnTimeSetListener {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "hourOfDay : "+hourOfDay+"minute : "+minute, 1).show();
		}

	}

	public class MyDatePickerDialog implements DatePickerDialog.OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "year : "+year+"monthOfYear : "+monthOfYear+"dayOfMonth : "+dayOfMonth, 1).show();
		}

	}

}
