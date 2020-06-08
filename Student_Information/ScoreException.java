package Student_Information;

public class ScoreException extends Exception
{
    public ScoreException(){};
    void ShowError()
    {
        System.out.println("学生成绩有问题");
    }
}

