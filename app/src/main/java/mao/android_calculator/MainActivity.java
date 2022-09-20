package mao.android_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String TAG = "MainActivity";
    private TextView tv_result;
    // 第一个操作数
    private String firstNum = "";
    // 运算符
    private String operator = "";
    // 第二个操作数
    private String secondNum = "";
    // 当前的计算结果
    private String result = "";
    // 显示的文本内容
    private String showText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 从布局文件中获取名叫tv_result的文本视图
        tv_result = findViewById(R.id.tv_result);
        // 下面给每个按钮控件都注册了点击监听器
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        // “除法”按钮
        findViewById(R.id.btn_divide).setOnClickListener(this);
        // “乘法”按钮
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        // “清除”按钮
        findViewById(R.id.btn_clear).setOnClickListener(this);
        // 数字7
        findViewById(R.id.btn_seven).setOnClickListener(this);
        // 数字8
        findViewById(R.id.btn_eight).setOnClickListener(this);
        // 数字9
        findViewById(R.id.btn_nine).setOnClickListener(this);
        // “加法”按钮
        findViewById(R.id.btn_plus).setOnClickListener(this);
        // 数字4
        findViewById(R.id.btn_four).setOnClickListener(this);
        // 数字5
        findViewById(R.id.btn_five).setOnClickListener(this);
        // 数字6
        findViewById(R.id.btn_six).setOnClickListener(this);
        // “减法”按钮
        findViewById(R.id.btn_minus).setOnClickListener(this);
        // 数字1
        findViewById(R.id.btn_one).setOnClickListener(this);
        // 数字2
        findViewById(R.id.btn_two).setOnClickListener(this);
        // 数字3
        findViewById(R.id.btn_three).setOnClickListener(this);
        // 求倒数按钮
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        // 数字0
        findViewById(R.id.btn_zero).setOnClickListener(this);
        // “小数点”按钮
        findViewById(R.id.btn_dot).setOnClickListener(this);
        // “等号”按钮
        findViewById(R.id.btn_equal).setOnClickListener(this);
        // “开平方”按钮
        findViewById(R.id.ib_sqrt).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v)
    {
        String inputText;
        // 如果是开根号按钮
        if (v.getId() == R.id.ib_sqrt)
        {
            inputText = "√";
        }
        else
        {
            // 除了开根号之外的其他按钮
            inputText = ((TextView) v).getText().toString();
        }
        switch (v.getId())
        {
            // 点击了清除按钮
            case R.id.btn_clear:
                clear();
                break;
            // 点击了取消按钮
            case R.id.btn_cancel:
                break;
            // 点击了加、减、乘、除按钮
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                plus_minus_multiply_divide(inputText);
                break;
            // 点击了等号按钮
            case R.id.btn_equal:
                equal();
                break;
            // 点击了开根号按钮
            case R.id.ib_sqrt:
                sqrt();
                break;
            // 点击了求倒数按钮
            case R.id.btn_reciprocal:
                reciprocal();
                break;
            // 点击了其他按钮，包括数字和小数点
            default:
                caseDefault(inputText);
        }

    }

    /**
     * 加减乘除
     *
     * @param inputText 输入文本
     */
    private void plus_minus_multiply_divide(String inputText)
    {
        if (firstNum.equals("."))
        {
            return;
        }
        operator = inputText; // 运算符
        if (!secondNum.equals(""))
        {
            double result = calculateFour();
            this.result = String.valueOf(result);
            firstNum = this.result;
            secondNum = "";

        }
        refreshText(showText + operator);
        Log.d(TAG, "onClick: showText=" + showText
                + ", operator=" + operator);
        Log.d(TAG, "onClick: firstNum="
                + firstNum + ",secondNum=" + secondNum);
        Log.d(TAG, "onClick: result=" + result);
        return;
    }

    /**
     * caseDefault
     *
     * @param inputText 输入文本
     */
    private void caseDefault(String inputText)
    {
        // 上次的运算结果已经出来了
        if (result.length() > 0 && operator.equals(""))
        {
            clear();
        }

        // 无运算符，则继续拼接第一个操作数
        if (operator.equals(""))
        {
            firstNum = firstNum + inputText;
        }
        else
        {
            // 有运算符，则继续拼接第二个操作数
            secondNum = secondNum + inputText;
        }
        // 整数不需要前面的0
        if (showText.equals("0") && !inputText.equals("."))
        {
            refreshText(inputText);
        }
        else
        {
            refreshText(showText + inputText);
        }
        return;
    }


    /**
     * 等号
     */
    private void equal()
    {
        // 加减乘除四则运算
        double calculate_result = calculateFour();
        refreshOperate(String.valueOf(calculate_result));
        refreshText(showText + "=" + result);
        return;
    }

    /**
     * 求倒数
     */
    private void reciprocal()
    {
        if (firstNum.equals("."))
        {
            return;
        }
        double reciprocal_result;
        if (firstNum.equals(""))
        {
            reciprocal_result = 0;
        }
        else
        {
            reciprocal_result = 1.0 / Double.parseDouble(firstNum);
        }
        refreshOperate(String.valueOf(reciprocal_result));
        refreshText(showText + "/=" + result);
        return;
    }


    /**
     * √
     */
    private void sqrt()
    {
        if (firstNum.equals("."))
        {
            return;
        }
        double sqrt_result;
        if (firstNum.equals(""))
        {
            sqrt_result = 0;
        }
        else
        {
            sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
        }
        refreshOperate(String.valueOf(sqrt_result));
        refreshText(showText + "√=" + result);
        return;
    }


    /**
     * 加减乘除四则运算，返回计算结果
     *
     * @return double
     */
    private double calculateFour()
    {
        if (firstNum.equals("") && secondNum.equals(""))
        {
            clear();
            return 0;
        }
        Log.d(TAG, "calculateFour: firstNum="
                + firstNum + ",secondNum=" + secondNum);
        switch (operator)
        {
            case "＋":
                if (secondNum.equals(""))
                {
                    return Double.parseDouble(firstNum);
                }
                if (firstNum.equals(""))
                {
                    return Double.parseDouble(secondNum);
                }
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "－":
                if (secondNum.equals(""))
                {
                    return Double.parseDouble(firstNum);
                }
                //-7
                if (firstNum.equals(""))
                {
                    return -1 * (Double.parseDouble(secondNum));
                }
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "×":
                if (secondNum.equals(""))
                {
                    return Double.parseDouble(firstNum);
                }
                if (firstNum.equals(""))
                {
                    return Double.parseDouble(secondNum);
                }
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            default:
                if (secondNum.equals(""))
                {
                    return Double.parseDouble(firstNum);
                }
                if (firstNum.equals(""))
                {
                    return Double.parseDouble(secondNum);
                }
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
    }

    /**
     * 清空并初始化
     */
    private void clear()
    {
        refreshOperate("");
        refreshText("");
    }

    /**
     * 刷新运算结果
     *
     * @param new_result 新结果
     */
    private void refreshOperate(String new_result)
    {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    /**
     * 刷新文本显示
     *
     * @param text 文本
     */
    private void refreshText(String text)
    {
        showText = text;
        tv_result.setText(showText);
    }
}