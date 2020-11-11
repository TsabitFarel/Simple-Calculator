package idn.tsabit.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        txt_input.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        txt_input.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            txt_input.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(txt_input.text.toString())) {
            txt_input.append((view as Button).text)

            lastNumeric = false
            lastDot = false
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var txtValue = txt_input.text.toString()
            var prefix = ""
            try {
                if (txtValue.startsWith("-")) {
                    prefix = "-"
                    txtValue = txtValue.substring(1)
                }
                if (txtValue.contains("-")) {
                    val splitValue = txtValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    txt_input.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if (txtValue.contains("+")) {
                        val splitValue = txtValue.split("+")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }

                        txt_input.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if (txtValue.contains("X")) {
                        val splitValue = txtValue.split("X")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }

                        txt_input.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                } else {
                    if (txtValue.contains("/")) {
                        val splitValue = txtValue.split("/")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }

                        txt_input.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                }

                
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("X") || value.contains("+")
                    || value.contains("-")
        }
    }
}