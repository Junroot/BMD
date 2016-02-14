import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject;
import loot.graphics.TextBox;


public class MainFrame extends GameFrame
{
	class Ground extends DrawableObject
	{

		int x = 136;
		int y = 24;
		int width = 1008;
		int height = 672;
		int oneblock = 56;
		int halfblock = 28;
		int cp = 22;
		public Ground()
		{
			super(136,24,1008,672,images.GetImage("ground"));
		}
	}

	class Background extends DrawableObject
	{
		Image[] imgs;
		public Background()
		{
			super(0,0,1280,720,images.GetImage("intro1"));
			imgs = new Image[7];
			imgs[0] = images.GetImage("intro1");
			imgs[1] = images.GetImage("intro2");
			imgs[2] = images.GetImage("intro3");
			imgs[3] = images.GetImage("intro4");
			imgs[4] = images.GetImage("intro5");
			imgs[5] = images.GetImage("intro6");
			imgs[6] = images.GetImage("intro7");
		}
	}

	class Alert extends DrawableObject
	{
		int status;
		Image[] imgs;
		public Alert()
		{
			super(260,238,749,244,images.GetImage("ready1"));
			imgs = new Image[6];
			status = -1;
			imgs[0] = images.GetImage("ready1");
			imgs[1] = images.GetImage("ready2");
			imgs[2] = images.GetImage("ready3");
			imgs[3] = images.GetImage("ready4");
			imgs[4] = images.GetImage("ready5");
			imgs[5] = images.GetImage("ready6");
		}
	}

	int isstart; // 1이면 시작
	double entertime;
	double starttime;
	double nowtime;
	int iscountdown;
	Ground ground;
	Background background;
	Player[] players;
	Player player1;
	Player player2;
	Block[][] blocks;
	Alert alert;
	ArrayList<Item> items;
	TextBox gamestatus;
	DrawableObject resultbox;
	DrawableObject frame;
	DrawableObject gauge;
	DrawableObject result;
	int[][] map_data =
		{
			{0,0,1,1,1,0,1,1,0,0,1,1,0,1,1,1,0,0},
			{0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0},
			{1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1},
			{1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,0,1},
			{1,1,1,1,2,2,2,2,0,0,2,2,2,2,1,1,1,1},
			{0,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,0},
			{0,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,0},
			{1,1,1,1,2,2,2,2,0,0,2,2,2,2,1,1,1,1},
			{1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1},
			{1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0},
			{0,0,1,1,1,0,1,1,0,0,1,1,0,1,1,1,0,0}
		};

	public boolean Move(Player p ,int dir,double timeStamp)
	{
		p.dir = dir;
		if (dir == 0) // 위
		{
			p.image = p.up_imgs[((int)timeStamp/100)%4];
			p.py -= p.vel;
			if (p.py <= ground.y)
			{
				p.py = ground.y;
				return false;
			}
			int up_x = p.block_x;
			int up_y = p.block_y - 1;
			int up_right_x = p.block_x + 1;
			int up_right_y = p.block_y - 1;

			if ((p.x - 136) % 56 == 0)
				up_right_x--;

			if (((int)p.py - 24) / 56 == p.block_y)
				return true;

			if (up_y != -1 && blocks[up_x][up_y].status != 0)
			{
				if (p.px - blocks[up_x][up_y].px <= 56 && p.px - blocks[up_x][up_y].px >= 56 - ground.cp && up_right_x != 18 && up_right_y != -1 && blocks[up_right_x][up_right_y].status == 0)
				{
					p.px = ground.x + ground.oneblock * (p.block_x + 1);
					return true;
				}
				p.py = ground.y + ground.oneblock * p.block_y;
				return false;
			}

			if (up_right_x != 18 && up_right_y != -1 && blocks[up_right_x][up_right_y].status != 0)
			{
				if(blocks[up_right_x][up_right_y].px - p.px <= 56 && blocks[up_right_x][up_right_y].px - p.px >= 56 - ground.cp && up_y != -1 && blocks[up_x][up_y].status == 0)
				{
					p.px = ground.x + ground.oneblock * p.block_x;
					return true;
				}
				p.py = ground.y + ground.oneblock * p.block_y;
				return false;
			}
		}
		else if (dir == 1) //오른쪽
		{
			p.image = p.right_imgs[((int)timeStamp/100)%4];
			p.px += p.vel;
			if (p.px - ground.x >= ground.width - ground.oneblock)
			{
				p.px = ground.x + ground.width - ground.oneblock;
				return false;
			}

			int right_x = p.block_x + 1;
			int right_y = p.block_y;
			int right_down_x = p.block_x + 1;
			int right_down_y = p.block_y + 1;

			if ((p.y - 24 - 28) % 56 == 0)
				right_down_y--;

			if (right_x != 18 && blocks[right_x][right_y].status != 0)
			{
				if (p.py - blocks[right_x][right_y].py <= 56  && p.py - blocks[right_x][right_y].py >=  56 - ground.cp && right_down_x != 18 && right_down_y != 12 && blocks[right_down_x][right_down_y].status == 0)
				{
					p.py = ground.y + ground.oneblock * (p.block_y + 1); 
					return true;
				}
				p.px = ground.x + ground.oneblock * p.block_x;
				return false;
			}

			if (right_down_x != 18 && right_down_y != 12 && blocks[right_down_x][right_down_y].status != 0)
			{
				if (blocks[right_down_x][right_down_y].py - p.py <= 56 && blocks[right_down_x][right_down_y].py - p.py >= 56 - ground.cp && right_x != 18 && blocks[right_x][right_y].status == 0)
				{	
					p.py = ground.y + ground.oneblock * p.block_y; 
					return true;
				}
				p.px = ground.x + ground.oneblock * p.block_x;
				return false;
			}
		}
		else if (dir == 2) //아래
		{
			p.image = p.down_imgs[((int)timeStamp/100)%4];
			p.py += p.vel;
			if (ground.y + ground.height - p.py <= ground.oneblock)
			{
				p.py = ground.y + ground.height - ground.oneblock;
				return false;
			}
			int down_x = p.block_x;
			int down_y = p.block_y + 1;
			int down_right_x = p.block_x + 1;
			int down_right_y = p.block_y + 1;

			if ((p.x - 136) % 56 == 0 )
				down_right_x--;

			if (down_y != 12 && blocks[down_x][down_y].status != 0)
			{
				if (p.px - blocks[down_x][down_y].px <= 56 && p.px - blocks[down_x][down_y].px >= 56 - ground.cp && down_right_x != 18 && down_right_y != 12 && blocks[down_right_x][down_right_y].status == 0)
				{
					p.px = ground.x + ground.oneblock * (p.block_x + 1);
					return true;
				}
				p.py = ground.y + ground.oneblock * p.block_y;
				return false;
			}

			if (down_right_x != 18 && down_right_y != 12 && blocks[down_right_x][down_right_y].status != 0)
			{
				if (blocks[down_right_x][down_right_y].px - p.px <= 56 && blocks[down_right_x][down_right_y].px - p.px >= 56 - ground.cp && down_y != 12 && blocks[down_x][down_y].status == 0)
				{
					p.px = ground.x + ground.oneblock * p.block_x;
					return true;
				}
				p.py = ground.y + ground.oneblock * p.block_y;
				return false;
			}
		}
		else if (dir == 3) //왼쪽
		{
			p.image = p.left_imgs[((int)timeStamp/100)%4];
			p.px -= p.vel;
			if (p.px <= ground.x)
			{
				p.px = ground.x;
				return false;
			}

			int left_x = p.block_x - 1;
			int left_y = p.block_y;
			int left_down_x = p.block_x - 1;
			int left_down_y = p.block_y + 1;

			if ((p.y - 24 - 28) % 56 == 0)
				left_down_y--;

			if (((int)p.px - 136) / 56 == p.block_x)
				return true;

			if (left_x != -1 && blocks[left_x][left_y].status != 0)
			{
				if (p.py - blocks[left_x][left_y].py <= 56 && p.py - blocks[left_x][left_y].py >= 56 - ground.cp && left_down_x != -1 && left_down_y != 12 && blocks[left_down_x][left_down_y].status == 0)
				{
					p.py = ground.y + ground.oneblock * (p.block_y + 1); 
					return true;
				}
				p.px = ground.x + ground.oneblock * p.block_x;
				return false;
			}

			if (left_down_x != -1 && left_down_y != 12 && blocks[left_down_x][left_down_y].status != 0)
			{
				if (blocks[left_down_x][left_down_y].py - p.py <= 56 && blocks[left_down_x][left_down_y].py - p.py >= 56 - ground.cp && left_x != -1 && blocks[left_x][left_y].status == 0)
				{
					p.py = ground.y + ground.oneblock * p.block_y; 
					return true;
				}
				p.px = ground.x + ground.oneblock * p.block_x;
				return false;
			}
		}
		return true;
	}

	public MainFrame(GameFrameSettings settings)
	{
		super(settings);
	}

	@Override
	public boolean Initialize()
	{
		audios.LoadAudio("Audios/bgm_ingame.wav", "gamebgm", 1);
		audios.LoadAudio("Audios/bgm_intro.wav", "introbgm", 1);
		audios.LoadAudio("Audios/bgm_start.wav", "ready", 1);
		audios.LoadAudio("Audios/itemget.wav", "itemget", 9);
		audios.LoadAudio("Audios/puck.wav", "big", 9);
		audios.LoadAudio("Audios/shoot.wav", "shoot", 9);
		audios.LoadAudio("Audios/pang.wav", "pang", 9);
		audios.LoadAudio("Audios/bgm_countdown.wav", "countdown", 1);
		images.LoadImage("Images/result.png", "result");
		images.LoadImage("Images/block_not_removable.png", "not_removable");
		images.LoadImage("Images/block_removable_1.png", "removable1");
		images.LoadImage("Images/block_removable_2.png", "removable2");
		images.LoadImage("Images/block_removable_3.png", "removable3");
		images.LoadImage("Images/block_not_exist.png", "not_exist");
		images.LoadImage("Images/char_down_0.png", "char_down_0");
		images.LoadImage("Images/char_down_1.png", "char_down_1");
		images.LoadImage("Images/char_down_2.png", "char_down_2");
		images.LoadImage("Images/char_down_3.png", "char_down_3");
		images.LoadImage("Images/char_down_4.png", "char_down_4");
		images.LoadImage("Images/char_up_0.png", "char_up_0");
		images.LoadImage("Images/char_up_1.png", "char_up_1");
		images.LoadImage("Images/char_up_2.png", "char_up_2");
		images.LoadImage("Images/char_up_3.png", "char_up_3");
		images.LoadImage("Images/char_up_4.png", "char_up_4");
		images.LoadImage("Images/char_left_0.png", "char_left_0");
		images.LoadImage("Images/char_left_1.png", "char_left_1");
		images.LoadImage("Images/char_left_2.png", "char_left_2");
		images.LoadImage("Images/char_left_3.png", "char_left_3");
		images.LoadImage("Images/char_left_4.png", "char_left_4");
		images.LoadImage("Images/char_right_0.png", "char_right_0");
		images.LoadImage("Images/char_right_1.png", "char_right_1");
		images.LoadImage("Images/char_right_2.png", "char_right_2");
		images.LoadImage("Images/char_right_3.png", "char_right_3");
		images.LoadImage("Images/char_right_4.png", "char_right_4");
		images.LoadImage("Images/Ground.png", "ground");
		images.LoadImage("Images/Bomb.png", "bomb");
		images.LoadImage("Images/explosion.png", "boooom");
		images.LoadImage("Images/item_boom.png", "item_boom");
		images.LoadImage("Images/item_power.png", "item_power");
		images.LoadImage("Images/ingame_frame.png", "frame");
		images.LoadImage("Images/gauge.png", "gauge");
		images.LoadImage("Images/bomb_chick.png", "bomb1");
		images.LoadImage("Images/bomb_hamburg.png", "bomb2");
		images.LoadImage("Images/bomb_pizza.png", "bomb3");
		images.LoadImage("Images/bomb_zazang.png", "bomb4");
		images.LoadImage("Images/Intro1.png", "intro1");
		images.LoadImage("Images/Intro2.png", "intro2");
		images.LoadImage("Images/Intro3.png", "intro3");
		images.LoadImage("Images/Intro4.png", "intro4");
		images.LoadImage("Images/Intro5.png", "intro5");
		images.LoadImage("Images/Intro6.png", "intro6");
		images.LoadImage("Images/Intro7.png", "intro7");
		images.LoadImage("Images/item_coin_1.png", "coin1");
		images.LoadImage("Images/item_coin_2.png", "coin2");
		images.LoadImage("Images/item_coin_3.png", "coin3");
		images.LoadImage("Images/item_coin_4.png", "coin4");
		images.LoadImage("Images/item_coupon_1.png", "coupon1");
		images.LoadImage("Images/item_coupon_2.png", "coupon2");
		images.LoadImage("Images/item_coupon_3.png", "coupon3");
		images.LoadImage("Images/item_coupon_4.png", "coupon4");
		images.LoadImage("Images/item_capsule_1.png", "diet1");
		images.LoadImage("Images/item_capsule_2.png", "diet2");
		images.LoadImage("Images/item_capsule_3.png", "diet3");
		images.LoadImage("Images/item_capsule_4.png", "diet4");
		images.LoadImage("Images/ready1.png", "ready1");
		images.LoadImage("Images/ready2.png", "ready2");
		images.LoadImage("Images/ready3.png", "ready3");
		images.LoadImage("Images/ready4.png", "ready4");
		images.LoadImage("Images/ready5.png", "ready5");
		images.LoadImage("Images/ready6.png", "ready6");
		images.LoadImage("Images/ready6.png", "ready6");
		images.LoadImage("Images/char_eat_1.png", "eat");
		images.LoadImage("Images/draw.png", "draw");
		images.LoadImage("Images/1win.png", "1win");
		images.LoadImage("Images/2win.png", "2win");
		background = new Background();
		ground = new Ground();
		blocks = new Block[18][12];
		players = new Player[2];
		items = new ArrayList<Item>();
		alert = new Alert();
		result = new DrawableObject(130,50,1020,620,images.GetImage("result"));
		player1 = new Player(0,0, images, audios, 1);
		player2 = new Player(17,11,images, audios, 2);
		resultbox = new DrawableObject(0,120,1375,352,images.GetImage("draw"));
		players[0] = player1;
		players[1] = player2;

		inputs.BindKey(KeyEvent.VK_R, 0);
		inputs.BindKey(KeyEvent.VK_G, 1);
		inputs.BindKey(KeyEvent.VK_F, 2);
		inputs.BindKey(KeyEvent.VK_D, 3);
		inputs.BindKey(KeyEvent.VK_CONTROL, 4);

		inputs.BindKey(KeyEvent.VK_UP, 5);
		inputs.BindKey(KeyEvent.VK_RIGHT, 6);
		inputs.BindKey(KeyEvent.VK_DOWN, 7);
		inputs.BindKey(KeyEvent.VK_LEFT, 8);
		inputs.BindKey(KeyEvent.VK_SLASH, 9);

		inputs.BindKey(KeyEvent.VK_ENTER, 10);

		gamestatus = new TextBox(ground.x+ground.width+5,ground.y + 5, ground.x-10,ground.height-10);
		frame = new DrawableObject(134,22,1012,676,images.GetImage("frame"));
		gauge = new DrawableObject(134,698,1012,22,images.GetImage("gauge"));

		for(int i=0;i<18;++i)
		{
			for(int j=0;j<12;++j)
			{
				Block block;
				if (map_data[j][i] == 1) block = new Block(i,j,56,64,map_data[j][i], images);
				else if (map_data[j][i] == 2) block = new Block(i,j,56,64,map_data[j][i], images);
				else	block = new Block(i,j,56,56,map_data[j][i], images);
				blocks[i][j] = block;
			}
		}
		audios.Loop("introbgm", -1);
		isstart = 3;
		iscountdown = 1;
		return true;
	}

	@Override
	public boolean Update(long timeStamp)
	{
		inputs.AcceptInputs();
		if (isstart == 3)
		{
			background.image = background.imgs[((int)(timeStamp/100))%7];
			if (inputs.buttons[10].isChanged == true)
			{
				if (inputs.buttons[10].isPressed == true)
				{
					isstart = 2;
					entertime = timeStamp;
					starttime = timeStamp;
					nowtime = timeStamp;
					audios.Stop("introbgm");
					audios.Loop("ready", 0);
				}
			}
		}
		else if (isstart == 2)
		{
			if (timeStamp - entertime >= 100 && alert.status == -1)
			{
				alert.status = 0;
			}
			else if (timeStamp - entertime >= 125 && alert.status == 0)
			{
				alert.image = alert.imgs[1];
				alert.status = 1;
			}
			else if (timeStamp - entertime >= 150 && alert.status == 1)
			{
				alert.image = alert.imgs[2];
				alert.status = 2;
			}
			else if (timeStamp - entertime >= 175 && alert.status == 2)
			{
				alert.image = alert.imgs[3];
				alert.status = 3;
			}
			else if (timeStamp - entertime >= 200 && alert.status == 3)
			{
				alert.image = alert.imgs[4];
				alert.status = 4;
			}
			else if (timeStamp - entertime >= 1075 && alert.status == 4)
			{
				alert.x = 463;
				alert.y = 248;
				alert.width = 353;
				alert.height = 223;
				alert.image = alert.imgs[5];
				alert.status = 5;
			}
			else if (timeStamp - entertime >= 2000)
			{
				isstart = 0;
				audios.Loop("gamebgm", 0);
				starttime = timeStamp;
				nowtime = timeStamp;
			}
		}
		else if (isstart == 0)
		{
			nowtime = timeStamp;
			if (inputs.buttons[0].isPressed == true) Move(player1,0,timeStamp);
			else if (inputs.buttons[1].isPressed == true) Move(player1,1,timeStamp);
			else if (inputs.buttons[2].isPressed == true) Move(player1,2,timeStamp);
			else if (inputs.buttons[3].isPressed == true) Move(player1,3,timeStamp);
			else
			{
				player1.image = player1.stop_imgs[player1.dir];
			}

			if (inputs.buttons[4].isChanged == true)
			{
				if (inputs.buttons[4].isPressed == true && player1.canhit == 0 && player1.canshoot == 1)
				{
					player1.ShootBomb(timeStamp);
				}
			}

			if (inputs.buttons[5].isPressed == true) Move(player2,0,timeStamp);
			else if (inputs.buttons[6].isPressed == true) Move(player2,1,timeStamp);
			else if (inputs.buttons[7].isPressed == true) Move(player2,2,timeStamp);
			else if (inputs.buttons[8].isPressed == true) Move(player2,3,timeStamp);
			else
			{
				player2.image = player2.stop_imgs[player2.dir];
			}

			if (inputs.buttons[9].isChanged == true)
			{
				if (inputs.buttons[9].isPressed == true && player2.canhit == 0 && player2.canshoot == 1)
				{
					player2.ShootBomb(timeStamp);
				}
			}
			if (nowtime-starttime >= 120*1000 - 11*1000 && iscountdown == 1)
			{
				iscountdown = 0;
				audios.Loop("countdown", 0);
			}
			if (nowtime-starttime >= 120*1000)
			{

				isstart = -1;
			}
		}
		else if (isstart == -1)
		{
			if (inputs.buttons[10].isChanged == true)
			{
				if (inputs.buttons[10].isPressed == true)	System.exit(0);
			}
			int topkcal = player1.kcal;
			int top = 0;
			for (int i = 1;i<players.length;++i)
			{
				if (topkcal > players[i].kcal)
				{
					topkcal = players[i].kcal;
					top = i;
				}
				else if (topkcal == players[i].kcal)
				{
					top = -1;
					break;
				}
			}
			if (top == -1)
			{
				resultbox = new DrawableObject(140,210,1000,300,images.GetImage("draw"));
			}
			else if (top == 0)
			{
				resultbox = new DrawableObject(140,210,1000,300,images.GetImage("1win"));
			}
			else if (top == 1)
			{
				resultbox = new DrawableObject(140,210,1000,300,images.GetImage("2win"));
			}
		}
		for (Player player : players)
		{
			for (Boooom boom : player.booms)
			{
				for (Player player1 : players)
				{
					if (boom.block_x == ((int)player1.px-ground.x + 28)/56 && boom.block_y == ((int)player1.py-ground.y +28)/56 && player1.canhit == 0)
					{
						audios.Loop("big", 0);
						player1.Hit(timeStamp,player);
					}
				}
			}
		}

		for (Player player : players)
		{
			player.Update(timeStamp, blocks, players, items);
		}
		for (Player player : players)
		{
			for (Iterator<Item> iterator = items.iterator();iterator.hasNext();)
			{
				Item item = (Item) iterator.next();
				if ((int)(player.px - ground.x + ground.halfblock)/56 == item.block_x && (int)(player.py - ground.y + ground.halfblock)/56 == item.block_y)
				{
					audios.Loop("itemget", 0);
					if (item.type == 0)
					{
						if (player.bomblimit <= 8)
							++(player.bomblimit);
					}
					else if (item.type == 1)
					{
						if (player.bomblong <= 6)
							++(player.bomblong);
					}
					else if (item.type == 2)
					{
						player.kcal -= 500;
					}
					iterator.remove();
				}
			}
		}
		for (Item item : items)
		{
			item.Update(timeStamp);
		}
		gauge.width = (int)(1012 * (120*1000-(nowtime-starttime))/120000);
		gamestatus.text = String.format("남은 시간 : %d : %d \n \n \n \n"
				+ "player1 : \n \n"
				+ "\n"
				+ "	칼로리 : %dkcal\n \n \n \n"
				+ "player2 : \n \n"
				+ "	칼로리 : %dkcal",(120 - (int)(nowtime-starttime)/1000) / 60, (120 - (int)(nowtime-starttime)/1000) % 60,player1.kcal, player2.kcal);

		return true;


	}


	@Override
	public void Draw(long timeStamp)
	{
		BeginDraw();
		ClearScreen();
		if (isstart == 3)
		{
			background.Draw(g);
		}
		if (isstart < 3)
		{
			frame.Draw(g);
			ground.Draw(g);
			for (Player player : players)
			{
				for(Boooom boom : player.booms)
				{
					boom.Draw(g);
				}
			}
			for(int j=0;j<12;++j)
			{
				for(int i=0;i<18;++i)
				{
					blocks[i][j].Draw(g);
				}
				for (Item item : items) if (j==item.block_y)	item.Draw(g);
				for (Player player : players)	if(j==player.block_y)	player.Draw(g);
				for (Player player : players)
				{
					for(Bomb bomb : player.bombs)
					{
						if (j==bomb.block_y) bomb.Draw(g);
					}
				}
			}
			if (isstart == 2)
			{
				if(alert.status != -1) alert.Draw(g);
			}
			gamestatus.Draw(g);
			gauge.Draw(g);
		}
		if(isstart == -1)
		{
			result.Draw(g);
			resultbox.Draw(g);
		}
		EndDraw();
	}
}
