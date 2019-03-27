using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Text.RegularExpressions;

namespace Calculator
{
    public partial class Form1 : Form
    {
        public string value1, op;
        public bool inputstatus;
        decimal MemoryStore = 0;

        public Form1()
        {
            InitializeComponent();
            value1 = "";
            textBox1.ReadOnly = true;
            //textBox1.RightToLeft = RightToLeft.Yes;
            //textBox2.ReadOnly = true;
           // textBox2.RightToLeft = RightToLeft.Yes;
            radioButton2.Checked = true;
        }

        private enum Precedence
        {
            None = 11,      //없음
            Power = 9,      // ^
            Times = 8,     // * / P C
            Div = 7,
            Modulus = 6,  //%
            Plus = 5,     //+ -
        }

        //copy value of button text into text box
        //button.text에 있는 값을 textbox2.text에 복사한다.

        private void button17_Click(object sender, EventArgs e)  //0
        {
            textBox2.Text += button17.Text;
        }

        private void button1_Click(object sender, EventArgs e)  //1
        {
            textBox2.Text += button1.Text;
        }

        private void button3_Click(object sender, EventArgs e)  //2
        {
            textBox2.Text += button3.Text;
        }

        private void button2_Click(object sender, EventArgs e)  //3
        {
            textBox2.Text += button2.Text;
        }

        private void button9_Click(object sender, EventArgs e)  //4
        {
            textBox2.Text += button9.Text;
        }

        private void button7_Click(object sender, EventArgs e)  //5
        {
            textBox2.Text += button7.Text;
        }

        private void button8_Click(object sender, EventArgs e)  //6
        {
            textBox2.Text += button8.Text;
        }

        private void button6_Click(object sender, EventArgs e)  //7
        {
            textBox2.Text += button6.Text;
        }

        private void button4_Click(object sender, EventArgs e) //8
        {
            textBox2.Text += button4.Text;
        }

        private void button5_Click(object sender, EventArgs e)  //9
        {
            textBox2.Text += button5.Text;
        }

        private void button18_Click(object sender, EventArgs e)  //.
        {
                    textBox2.Text += button18.Text;
        }

//operator value
        private void button16_Click(object sender, EventArgs e)  //더하기
        {
            textBox2.Text += button16.Text;
        }

        private void button14_Click(object sender, EventArgs e) //빼기
        {
            textBox2.Text += button14.Text;
        }

        private void button15_Click(object sender, EventArgs e)  //곱하기
        {
            textBox2.Text += "*";
        }

        private void button13_Click(object sender, EventArgs e)  //나누기
        {
            textBox2.Text += "/";
        }

        
        private void button11_Click(object sender, EventArgs e)  // =
        {
            // Get the expression.
            string expr = textBox2.Text;

            // Evaluate the expression.
            textBox1.Text = "";
           
            try
            {
                textBox1.Text = EvaluateExpression(expr).ToString();
                //input and result in 1 line code  textBox2.Text =textBox2.Text+" = "+ EvaluateExpression(expr).ToString();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void button12_Click(object sender, EventArgs e)  //AC = all clear
        {
            textBox1.Enabled = true;
            textBox1.Text = "";
            textBox2.Enabled = true;
            textBox2.Text = "";
        }

        private void button33_Click(object sender, EventArgs e)  //x^2
        {
            textBox2.Text += "^2";
        }

        private void button31_Click(object sender, EventArgs e)  //x^3
        {
            textBox2.Text += "^3";
        }

        private void button48_Click(object sender, EventArgs e)  //sqrt
        {
            textBox2.Text += button48.Text;
        }

        private void button32_Click(object sender, EventArgs e)  //x^y
        {
            textBox2.Text += button32.Text;
        }

        private void button24_Click(object sender, EventArgs e) //sin
        {
            textBox2.Text += button24.Text;
        }

        private void button22_Click(object sender, EventArgs e)  //cos
        {
            textBox2.Text += button22.Text;
        }

        private void button23_Click(object sender, EventArgs e)  //tan
        {
            textBox2.Text += button23.Text;
        }

        private void button29_Click(object sender, EventArgs e)  //1/x
        {
            textBox2.Text += "1/";
        }

        private void button27_Click(object sender, EventArgs e)  //x!
        {
            textBox2.Text += button27.Text;
        }

        private void button47_Click(object sender, EventArgs e)  //log10 x
        {
            textBox2.Text += "log";
        }

        private void button46_Click(object sender, EventArgs e)  //natural log
        {
            textBox2.Text += "natlog";
        }

        private void button10_Click(object sender, EventArgs e) //delete
        {
            value1 = textBox2.Text;
            int n = value1.Length;
            if(textBox2.Text.Length!=0){
                textBox2.Text = (value1.Substring(0, n - 1));
            }         
        }

        private void button28_Click(object sender, EventArgs e)  //%
        {
            textBox2.Text += button28.Text;
        }

        private void button30_Click(object sender, EventArgs e)  // +/-
        {
            string temp = textBox2.Text;
            if (temp.Substring(0).Equals('-')==false)
            {
                textBox2.Text = "-" + temp;
            }
            else
            {
                textBox2.Text = temp.Replace("-", "");
            }
        }

        private void button21_Click(object sender, EventArgs e)  //sin-1 (inverse)
        {
            textBox2.Text += button21.Text;
        }

        private void button19_Click(object sender, EventArgs e) //cos-1
        {
            textBox2.Text += button19.Text;
        }

        private void button20_Click(object sender, EventArgs e)  //tan-1
        {
            textBox2.Text += button20.Text;
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {

        }
         
        private void button25_Click(object sender, EventArgs e)  //npr (permutation)
        {
            textBox2.Text += button25.Text;
        }

        private void button26_Click(object sender, EventArgs e)  //ncr (combination)
        {
            textBox2.Text += button26.Text;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
        }

        private void button50_Click(object sender, EventArgs e)  //파일불러오기
        {
            StreamReader sr = new StreamReader(new FileStream("output.txt", FileMode.Open));
            string a = null;
            while(sr.EndOfStream==false)
            {
                a = sr.ReadLine();
            }
            textBox2.Text = a;
        }

        // Evaluate the expression.
        private double EvaluateExpression(string expression)
        {
            int best_pos = 0;
            int parens = 0;

            // 공간 게거 
            string expr = expression.Replace(" ", "");
            int expr_len = expr.Length;
            if (expr_len == 0) return 0;
           // bool is_unary = true;

            // precedence 없음
            Precedence best_prec = Precedence.None;

            // we are looking for the lowest precedence through loop
            // 가장 낮은 precedence 찾다.
            for (int pos = 0; pos < expr_len; pos++)
            {
                // Examine the next character.
                string ch = expr.Substring(pos, 1);
                //bool next_unary = false;

                if (ch == " ")
                {
                    // 공간 skip
                }
                else if (ch == "(")
                {
                    // Increase the open parentheses count.
                    parens += 1;
                    //next_unary = true;
                }
                else if (ch == ")")
                {
                    // Decrease the open parentheses count.
                    parens -= 1;
                    //next_unary = false;

                    // If parens < 0, too many )'s.
                    if (parens < 0)
                        throw new FormatException(
                            "Too many close parentheses in '" +
                            expression + "'");
                    }
                else if (parens == 0)  //가장 낮은 precedence 찾다.
                {    
                    if ((ch == "^") || (ch == "*") ||
                        (ch == "/") || (ch == "\\") ||
                        (ch == "%") || (ch == "+") ||
                        (ch == "-") || (ch== "P") ||
                        (ch== "C"))
                    {
                        //next_unary = true;

                        //operator 과 다음 operator의 precedence를 비교
                        switch (ch)
                        {
                            case "^":
                                if (best_prec >= Precedence.Power)
                                {
                                    best_prec = Precedence.Power; //lowest op 저장
                                    best_pos = pos;  //lowest prec 위치 저장
                                }
                                break;
                     
                            case "P":
                            case "C":  
                            case "*":
                            case "/":
                                if (best_prec >= Precedence.Times)
                                {
                                    best_prec = Precedence.Times;
                                    best_pos = pos;
                                }
                                break;

                            case "%":
                                if (best_prec >= Precedence.Modulus)
                                {
                                    best_prec = Precedence.Modulus;
                                    best_pos = pos;
                                }
                                break;

                            case "+":
                            case "-":
                                if (best_prec >= Precedence.Plus)
                                {
                                    best_prec = Precedence.Plus;
                                    best_pos = pos;
                                }
                                break;
                        } // End switch (ch)
                    } // End if this is an operator
                } // else if (parens == 0)

                //is_unary = next_unary;
            } // for (int pos = 0; pos < expr_len; pos++)

            // missing ")"
            if (parens != 0)
            {
                throw new FormatException(
                    "Missing close parenthesis in '" +
                    expression + "'");
            }

            // 재귀함수로 expression을 evaluate한다.
            if (best_prec < Precedence.None)
            {
                string lexpr = expr.Substring(0, best_pos); //left exp
                string rexpr = expr.Substring(best_pos + 1); //right exp
                switch (expr.Substring(best_pos, 1))
                {
                    case "^":
                        return Math.Pow(
                            EvaluateExpression(lexpr), //(..left exp) ^ (right exp..)
                            EvaluateExpression(rexpr)); //left& right exp은 재귀함수로 다시 evaluate
                    case "*":
                        return
                            EvaluateExpression(lexpr) *
                            EvaluateExpression(rexpr);
                    case "/":
                        return
                            EvaluateExpression(lexpr) /
                            EvaluateExpression(rexpr);
                    case "%":
                        return
                            EvaluateExpression(lexpr) %
                            EvaluateExpression(rexpr);
                    case "+":
                        return
                            EvaluateExpression(lexpr) +
                            EvaluateExpression(rexpr);
                    case "-":
                        return
                            EvaluateExpression(lexpr) -
                            EvaluateExpression(rexpr);
                    case "P":
                        return
                            Factorial(EvaluateExpression(lexpr)) /
                            Factorial(EvaluateExpression(lexpr)-EvaluateExpression(rexpr));
                    case "C":
                        return
                            Factorial(EvaluateExpression(lexpr)) /
                            (Factorial( EvaluateExpression(rexpr)) * Factorial(EvaluateExpression(lexpr) - EvaluateExpression(rexpr)));
                }
            }

            // operator을 찾을 수 없으면 몇의 기능성이 있다(there are several possibillities):
            // 1. expr is (expr2) for some expr2.
            // 2. expr is -expr2 or +expr2 for some expr2.
            // 3. expr is Fun(expr2) for a function Fun.
            //위에 있는 문자가 어려워서 한국말로 설명할 수 없다.

            // Look for (expr2).
            if (expr.StartsWith("(") && expr.EndsWith(")"))
            {
                // Remove the parentheses.
                return EvaluateExpression(expr.Substring(1, expr_len - 2));
            }

            // Look for -expr2.
            if (expr.StartsWith("-"))
            {
                return -EvaluateExpression(expr.Substring(1));
            }

            // Look for +expr2.
            if (expr.StartsWith("+"))
            {
                return EvaluateExpression(expr.Substring(1));
            }

            //function(expr2)은 숫자와문자를 split한다. cos25 -> "cos" "25"
                var numAlpha = new Regex("(?<Alpha>[a-zA-Z]*)(?<Numeric>[0-9]*)(?<Factorial>[!]*)");
            // create string and numeral pattern
                var match = numAlpha.Match(expr);

                var alpha = match.Groups["Alpha"].Value;  //alphabet
                var num = match.Groups["Numeric"].Value;  //number
                var fac = match.Groups["Factorial"].Value; //! sign

                    // See what the function is.
                    switch (alpha.ToLower())
                    {
                        case "sin":
                            if (radioButton1.Checked == true) //radian
                            {
                                return Math.Sin(EvaluateExpression(num)); //재귀한수로 다시 evaluate
                            }
                            else  //degree
                            {
                                return Math.Sin((Math.PI / 180) * EvaluateExpression(num));
                            }
                        case "cos":
                            if (radioButton1.Checked == true)
                            {
                                return Math.Cos(EvaluateExpression(num));
                            }
                            else
                            {
                                return Math.Cos((Math.PI/180)*EvaluateExpression(num));
                            }
                        case "tan":
                            if (radioButton1.Checked == true)
                            {
                                return Math.Tan(EvaluateExpression(num));
                            }
                            else
                            {
                                return Math.Tan((Math.PI / 180) * EvaluateExpression(num));
                            }
                        case "sin-1":
                            if (radioButton1.Checked == true) //radian
                            {
                                return Math.Asin(EvaluateExpression(num));
                            }
                            else  //degree
                            {
                                return Math.Asin((Math.PI / 180) * EvaluateExpression(num));
                            }
                        case "cos-1":
                            if (radioButton1.Checked == true)
                            {
                                return Math.Acos(EvaluateExpression(num));
                            }
                            else
                            {
                                return Math.Acos((Math.PI / 180) * EvaluateExpression(num));
                            }
                        case "tan-1":
                            if (radioButton1.Checked == true)
                            {
                                return Math.Atan(EvaluateExpression(num));
                            }
                            else
                            {
                                return Math.Atan((Math.PI / 180) * EvaluateExpression(num));
                            }
                        case "sqrt":
                            return Math.Sqrt(EvaluateExpression(num));
                        case "log":
                            return Math.Log10(EvaluateExpression(num));
                        case "natlog":
                            return Math.Log(EvaluateExpression(num));
                    }

                    switch(fac.ToLower())
                      {
                        case "!":
                            return Factorial(EvaluateExpression(num));
                    }

            try
            {
                // Try to convert the expression into a Double.
                return double.Parse(expr);
            }
            catch (Exception)
            {
                throw new FormatException(
                    "Error evaluating '" + expression +
                    ". Input () or wrong operator");
            }
        }

        // Return the factorial of the expression.
        private double Factorial(double value)
        {
            if ((long)value != value)
            {
                throw new ArgumentException(
                    "Parameter to Factorial function must be an integer in Factorial(" +
                    value.ToString() + ")");
            }

            double result = 1;
            for (int i = 2; i <= value; i++)
            {
                result *= i;
            }
            return result;
        }

        private void button49_Click(object sender, EventArgs e)  //결과저장
        {
            using (StreamWriter sw = new StreamWriter("output.txt", true))  //append next line
            sw.WriteLine("{0} = {1}", textBox2.Text,textBox1.Text);
        }

        private void radioButton2_CheckedChanged_1(object sender, EventArgs e)
        {

        }

        private void radioButton1_CheckedChanged_1(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        //Memory저장 기능
        private void button37_Click(object sender, EventArgs e)  //M- (memory substract)
        {
            value1 = textBox2.Text;
            MemoryStore -= Convert.ToDecimal(value1);
            op = "M-";
            textBox2.Text = op + "" + value1;
            textBox1.Text = "";
        }

        private void button38_Click(object sender, EventArgs e)
        {
            textBox2.Text += button38.Text;
        }

        private void button39_Click(object sender, EventArgs e)  //M+ (memory add)
        {
            value1 = textBox2.Text;
            MemoryStore += Convert.ToDecimal(value1);
            op = "M+";
            textBox2.Text = op+""+value1;
            textBox1.Text = "";
        }

        private void button41_Click(object sender, EventArgs e)
        {
            textBox2.Text += button41.Text;
        }

        private void button43_Click(object sender, EventArgs e)  //MR (Memory recall)
        {
            textBox1.Text = MemoryStore.ToString();
            inputstatus = false;
            op = "MR";
            textBox2.Text = op;
        }

        private void button45_Click(object sender, EventArgs e)  //MC (memory clear)
        {
            MemoryStore = 0;
            inputstatus = false;
            op = "MC";
            textBox2.Text = op;
            textBox1.Text = "";
        }

        private void button34_Click(object sender, EventArgs e)
        {
            textBox2.Text += "3.14";
        } 
    }
}
