import java.awt.Color;
import loot.GameFrameSettings;



public class Program
{
	
	public static void main(String[] args)
	{
		GameFrameSettings settings = new GameFrameSettings();
		/* 여기서 settings. 을 입력하여 게임 화면 관련 설정 가능 */
		settings.canvas_backgroundColor = Color.WHITE;
		settings.canvas_width = 1280;
		settings.canvas_height = 720;
		settings.numberOfButtons = 50;
		MainFrame window = new MainFrame(settings); //SampleFrame 대신 여러분이 만든 클래스 이름 넣기
	    window.setTitle("밥은 먹고 다니니");
	    window.setVisible(true);
	}

}
