package info.fitapp.chart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import info.fitapp.chart.model.DataPoint
import info.fitapp.chart.model.DataSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataSet = DataSet()

        dataSet.items.add(DataPoint(11f, "Jan").apply { comparisonValue = 8f })
        dataSet.items.add(DataPoint(18f, "Feb").apply { comparisonValue = 9f })
        dataSet.items.add(DataPoint(2f, "Mar").apply { comparisonValue = 0f })
        dataSet.items.add(DataPoint(3f, "Apr").apply { comparisonValue = 5f })
        dataSet.items.add(DataPoint(4f, "May").apply { comparisonValue = 18f })
        dataSet.items.add(DataPoint(6f, "Jun").apply { comparisonValue = 5f })
        dataSet.items.add(DataPoint(12f, "Jul").apply { comparisonValue = 2f })
        dataSet.items.add(DataPoint(8f, "Aug").apply { comparisonValue = 1f })
        dataSet.items.add(DataPoint(10f, "Sep").apply { comparisonValue = 8f })
        dataSet.items.add(DataPoint(16f, "Oct").apply { comparisonValue = 10f })
        dataSet.items.add(DataPoint(17f, "Nov").apply { comparisonValue = 11f })
        dataSet.items.add(DataPoint(11f, "Dec").apply { comparisonValue = 12f })

        /*
        dataSet.items.add(DataPoint(10f, "Mo"))
        dataSet.items.add(DataPoint(15f, "Di"))
        dataSet.items.add(DataPoint(4f, "Mi"))
        dataSet.items.add(DataPoint(12f, "Do"))
        dataSet.items.add(DataPoint(23f, "Fr"))
        dataSet.items.add(DataPoint(0f, "Sa"))
        dataSet.items.add(DataPoint(16f, "So"))


        dataSet.items.add(DataPoint(100f, "Mo"))
        dataSet.items.add(DataPoint(150f, "Di"))
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
