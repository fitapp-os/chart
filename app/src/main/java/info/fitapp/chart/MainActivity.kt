package info.fitapp.chart

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import info.fitapp.chart.model.DataPoint
import info.fitapp.chart.model.DataSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataSet = DataSet()
        dataSet.items.add(DataPoint(10f, "Mo"))
        dataSet.items.add(DataPoint(15f, "Di"))
        dataSet.items.add(DataPoint(4f, "Mi"))
        dataSet.items.add(DataPoint(12f, "Do"))
        dataSet.items.add(DataPoint(23f, "Fr"))
        dataSet.items.add(DataPoint(0f, "Sa"))
        dataSet.items.add(DataPoint(16f, "So"))

        /*
        dataSet.items.add(DataPoint(10f, "Mo"))
        dataSet.items.add(DataPoint(15f, "Di"))
        dataSet.items.add(DataPoint(4f, "Mi"))
        dataSet.items.add(DataPoint(12f, "Do"))
        dataSet.items.add(DataPoint(23f, "Fr"))
        dataSet.items.add(DataPoint(0f, "Sa"))
        dataSet.items.add(DataPoint(16f, "So"))

        dataSet.items.add(DataPoint(10f, "Mo"))
        dataSet.items.add(DataPoint(15f, "Di"))
        dataSet.items.add(DataPoint(4f, "Mi"))
        dataSet.items.add(DataPoint(12f, "Do"))
        dataSet.items.add(DataPoint(23f, "Fr"))
        dataSet.items.add(DataPoint(0f, "Sa"))
        dataSet.items.add(DataPoint(16f, "So"))

        dataSet.items.add(DataPoint(10f, "Mo"))
        dataSet.items.add(DataPoint(15f, "Di"))
        dataSet.items.add(DataPoint(4f, "Mi"))
        dataSet.items.add(DataPoint(12f, "Do"))
        dataSet.items.add(DataPoint(23f, "Fr"))
        dataSet.items.add(DataPoint(0f, "Sa"))
        dataSet.items.add(DataPoint(16f, "So"))

        dataSet.items.add(DataPoint(10f, "Mo"))
        dataSet.items.add(DataPoint(15f, "Di"))
        dataSet.items.add(DataPoint(4f, "Mi"))
*/

        chart.setDataSet(dataSet)
        chart.setTypeface(ResourcesCompat.getFont(this, R.font.rubik_regular)!!)
    }
}
