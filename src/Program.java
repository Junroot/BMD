import java.awt.Color;
import loot.GameFrameSettings;



public class Program
{
	
	public static void main(String[] args)
	{
		GameFrameSettings settings = new GameFrameSettings();
		/* ���⼭ settings. �� �Է��Ͽ� ���� ȭ�� ���� ���� ���� */
		settings.canvas_backgroundColor = Color.WHITE;
		settings.canvas_width = 1280;
		settings.canvas_height = 720;
		settings.numberOfButtons = 50;
		MainFrame window = new MainFrame(settings); //SampleFrame ��� �������� ���� Ŭ���� �̸� �ֱ�
	    window.setTitle("���� �԰� �ٴϴ�");
	    window.setVisible(true);
	}

}
