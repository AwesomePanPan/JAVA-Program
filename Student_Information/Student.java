package Student_Information;

import java.awt.*;

public class Student
{
    private int ID;
    private String Name;
    private int Score1;
    private int Score2;
    private int Score3;
    private int AllScore;

    public Student(){}

    public Student(int id,String name,int score1,int score2,int score3,int allscore)
    {
        ID=id;Name=name;
        Score1=score1;Score2=score2;Score3=score3;AllScore=allscore;
    }

    public int GetID(){return ID;}
    public String GetName(){return Name;}
    public int GetScore1(){return Score1;}
    public int GetScore2(){return Score2;}
    public int GetScore3(){return Score3;}
    public int GetAllScore(){return AllScore;}
}
