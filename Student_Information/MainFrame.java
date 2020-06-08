package Student_Information;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

public class MainFrame extends JFrame
{
    //下面是界面需要的元素
    private static final int Width = 800;
    private static final int Height = 600;
    private JTextArea Text;
    private JScrollPane Roll;     //Text的滚动条
    private JButton InsertButton;
    private JButton SortButton;
    private JCheckBox Ascend;
    private JCheckBox Descend;
    private JLabel IDLabel;
    private JTextField FindText;
    private JButton FindButton;

    private JPanel Panel1;
    private JPanel Panel2;
    private JPanel Pfirst;
    private JPanel Pthird;

    //需要使用的数据
    private Student[] Stus=new Student[100];
    private boolean isRead = false;
    private String title;
    private int count = 0;

    public MainFrame()
    {
        setSize(Width, Height);   //设置尺寸
        setTitle("学生成绩管理系统");

        //给窗体元素分配空间
        InsertButton = new JButton("导入数据");
        Ascend = new JCheckBox("升序");
        Descend = new JCheckBox("降序");
        SortButton = new JButton("排序");
        IDLabel = new JLabel("学号: ");
        FindText = new JTextField(20);
        FindButton = new JButton("查找");
        Text = new JTextArea();
        Roll=new JScrollPane(Text);

        Panel1 = new JPanel();
        Panel2 = new JPanel();
        Pfirst = new JPanel();
        Pthird = new JPanel();


        //在面板上设置布局、添加元素
        Panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        Panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        Panel1.add(InsertButton);
        Panel2.add(Ascend);
        Panel2.add(Descend);
        Panel2.add(SortButton);

        Pfirst.setLayout(new GridLayout(1, 0));  //第一行的面板只有设置了一行，才可以使得上面的两个Panel按照流式布局走
        Pfirst.add(Panel1);
        Pfirst.add(Panel2);
        Pthird.add(IDLabel);
        Pthird.add(FindText);
        Pthird.add(FindButton);


        //在Frame上添加元素
        add(Pfirst, BorderLayout.NORTH);
        add(Roll,BorderLayout.CENTER);
        add(Pthird, BorderLayout.SOUTH);



        //监听器设置---导入数据监听器
        var InsertAction = new Insert();
        InsertButton.addActionListener(InsertAction);

        //监听器设置---排序监听器
        var SortAction=new Sort();
        SortButton.addActionListener(SortAction);

        //监听器设置---查找监听器
        var FindAction=new Find();
        FindButton.addActionListener(FindAction);
    }

    //内部类：Insert监听器
    class Insert implements ActionListener
    {
        public void readFileByLines(String fileName)
        {
            //这个作用是让按钮只会被触发一次，妙啊~
            if (isRead) return;
            //下面进行读文件操作
            BufferedReader reader = null;
            try {
                File file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                StringTokenizer st;    //用来分离String
                title = reader.readLine() + "\t总分";
                while ((tempString = reader.readLine()) != null) {
                    st = new StringTokenizer(tempString, "\t");
                    int id = Integer.parseInt(st.nextToken());
                    String name = st.nextToken();
                    int grade1 = Integer.parseInt(st.nextToken());
                    int grade2 = Integer.parseInt(st.nextToken());
                    int grade3 = Integer.parseInt(st.nextToken());
                    try
                    {
                        if(grade1>100||grade1<0||grade2>100||grade2<0||grade3>100||grade3<0)
                        {
                            throw new ScoreException();
                        }
                    }
                    catch (ScoreException ex)
                    {
                        System.out.print(name);
                        ex.ShowError();
                    }
                    int allgrade = grade1 + grade2 + grade3;
                    Stus[count] = new Student(id, name, grade1, grade2, grade3, allgrade);
                    count++;
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("文件没有找到");
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
            isRead = true;

        }
        public void ShowInTestArea()
        {
            Text.setText(title + "\n");

            for (int i = 0; i < count; i++) {
                String StudentLine = "";
                StudentLine += Stus[i].GetID() + "\t";
                StudentLine += Stus[i].GetName() + "\t";
                StudentLine += Stus[i].GetScore1() + "\t";
                StudentLine += Stus[i].GetScore2() + "\t";
                StudentLine += Stus[i].GetScore3() + "\t";
                StudentLine += Stus[i].GetAllScore();
                Text.append(StudentLine + "\n");
            }
        }
        public void actionPerformed(ActionEvent e)
            {
                String fileName = "C:/Users/91374/Desktop/example.txt";
                readFileByLines(fileName);
                ShowInTestArea();
            }


    }

    //内部类，sort监听器
    class Sort implements ActionListener
    {
        boolean Justices;    //防止一次同时选择两种排序操作

        public void SortStudents()
        {
            if(Ascend.isSelected())
            {
                Justices=true;
                for(int i=0;i<count;i++)
                {
                    for(int j=0;j<count-i-1;j++)
                    {
                        if(Stus[j].GetAllScore()>Stus[j+1].GetAllScore())
                        {
                            Student student = new Student();
                            student=Stus[j];
                            Stus[j]=Stus[j+1];
                            Stus[j+1]=student;
                        }
                    }
                }
            }
            if(Descend.isSelected())
            {
                Justices=true;
                for(int i=0;i<count;i++)
                {
                    for(int j=0;j<count-i-1;j++)
                    {
                        if(Stus[j].GetAllScore()<Stus[j+1].GetAllScore())
                        {
                            Student student = new Student();
                            student=Stus[j];
                            Stus[j]=Stus[j+1];
                            Stus[j+1]=student;
                        }
                    }
                }
            }
            if(Descend.isSelected()&&Ascend.isSelected())
            {
                Justices=false;
                String SortError="不可一次选择两项排序要求";
                Text.setText(SortError);
            }
        }
        public void ShowInTestArea() {
            if(Justices==true)
            {
                Text.setText(title + "\n");
                for (int i = 0; i < count; i++) {
                    String StudentLine = "";
                    StudentLine += Stus[i].GetID() + "\t";
                    StudentLine += Stus[i].GetName() + "\t";
                    StudentLine += Stus[i].GetScore1() + "\t";
                    StudentLine += Stus[i].GetScore2() + "\t";
                    StudentLine += Stus[i].GetScore3() + "\t";
                    StudentLine += Stus[i].GetAllScore();
                    Text.append(StudentLine + "\n");
                }
            }
        }
        public void actionPerformed(ActionEvent e)
        {
            SortStudents();
            ShowInTestArea();
        }
    }

    //内部类，Find监听器
    class Find implements ActionListener
    {

        public void FindStudent()
        {
            int id=0;
            Text.setText("");

            try
            {
              id=Integer.parseInt(FindText.getText());
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "请输入正确的学号");
                return;
            }

            for(int i=0;i<count;i++)
            {
                if(Stus[i].GetID()==id)
                {
                    Text.setText(title + "\n");
                    String StudentLine = "";
                    StudentLine += Stus[i].GetID() + "\t";
                    StudentLine += Stus[i].GetName() + "\t";
                    StudentLine += Stus[i].GetScore1() + "\t";
                    StudentLine += Stus[i].GetScore2() + "\t";
                    StudentLine += Stus[i].GetScore3() + "\t";
                    StudentLine += Stus[i].GetAllScore();
                    Text.append(StudentLine + "\n");
                }
            }

        }
        public void actionPerformed(ActionEvent e)
        {
            FindStudent();
        }
    }
}