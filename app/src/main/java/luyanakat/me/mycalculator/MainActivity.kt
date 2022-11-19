package luyanakat.me.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import luyanakat.me.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var resultTextView: TextView? = null
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultTextView = binding.resultTextView
    }

    fun onDigit(view: View) {
        resultTextView?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        resultTextView?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            resultTextView?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        resultTextView?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                resultTextView?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var resultTextV = resultTextView?.text.toString()
            var prefix = ""
        try {
            if (resultTextV.startsWith("-")) {
                prefix = "-"
                resultTextV = resultTextV.substring(1)
            }
            if (resultTextV.contains("-")) {
                val splitValue = resultTextV.split("-")
                var one = splitValue[0]
                var two = splitValue[1]

                if (prefix.isNotEmpty()) {
                    one = prefix + one
                }

                resultTextView?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
            } else if (resultTextV.contains("+")) {
                val splitValue = resultTextV.split("+")
                var one = splitValue[0]
                var two = splitValue[1]

                if (prefix.isNotEmpty()) {
                    one = prefix + one
                }

                resultTextView?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
            } else if (resultTextV.contains("/")) {
                val splitValue = resultTextV.split("/")
                var one = splitValue[0]
                var two = splitValue[1]

                if (prefix.isNotEmpty()) {
                    one = prefix + one
                }

                resultTextView?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
            } else if (resultTextV.contains("*")) {
                val splitValue = resultTextV.split("*")
                var one = splitValue[0]
                var two = splitValue[1]

                if (prefix.isNotEmpty()) {
                    one = prefix + one
                }

                resultTextView?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
            }

        } catch (e: java.lang.ArithmeticException) {
            e.printStackTrace()
        }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }
}