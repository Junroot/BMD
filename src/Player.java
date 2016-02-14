import java.awt.Image;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.annotation.Generated;

import loot.AudioManager;
import loot.ImageResourceManager;
import loot.graphics.DrawableObject;


public class Player extends DrawableObject
{
	int block_x, block_y; // ∞‘¿” ªÛ¿« ∫Ì∑∞¿« ¿ßƒ°
	double vel;
	int width, height;
	double px, py;
	int dir; // πÊ«‚
	int bomblimit, bomblong;
	int kcal;
	int canhit, canshoot; // 1¿Ã∏È π´¿˚, 1¿Ã∏È ΩÚºˆ ¿÷¿Ω
	double hittime;
	double shoottime;
	ArrayList<Bomb> bombs;
	ArrayList<Boooom> booms;
	Image bombimage, boomimage;
	ImageResourceManager images;
	AudioManager audios;
	Image[] stop_imgs;
	Image[] up_imgs;
	Image[] down_imgs;
	Image[] right_imgs;
	Image[] left_imgs;
	public Player(int x, int y, ImageResourceManager images, AudioManager audios, int playernum)
	{
		super(x*56 + 136, y*56 + 24 - 28, 56, 84, images.GetImage("char_down_0"));
		//		images.LoadImage("Images/char_down_0.png", "char_down_0");
		//		images.LoadImage("Images/char_down_1.png", "char_down_1");
		//		images.LoadImage("Images/char_down_2.png", "char_down_2");
		//		images.LoadImage("Images/char_down_3.png", "char_down_3");
		//		images.LoadImage("Images/char_down_4.png", "char_down_4");
		//		images.LoadImage("Images/char_up_0.png", "char_up_0");
		//		images.LoadImage("Images/char_up_1.png", "char_up_1");
		//		images.LoadImage("Images/char_up_2.png", "char_up_2");
		//		images.LoadImage("Images/char_up_3.png", "char_up_3");
		//		images.LoadImage("Images/char_up_4.png", "char_up_4");
		stop_imgs = new Image[4];
		stop_imgs[0] = images.GetImage("char_up_0");
		stop_imgs[1] = images.GetImage("char_right_0");
		stop_imgs[2] = images.GetImage("char_down_0");
		stop_imgs[3] = images.GetImage("char_left_0");
		up_imgs = new Image[4];
		up_imgs[0] = images.GetImage("char_up_1");
		up_imgs[1] = images.GetImage("char_up_2");
		up_imgs[2] = images.GetImage("char_up_3");
		up_imgs[3] = images.GetImage("char_up_4");
		down_imgs = new Image[4];
		down_imgs[0] = images.GetImage("char_down_1");
		down_imgs[1] = images.GetImage("char_down_2");
		down_imgs[2] = images.GetImage("char_down_3");
		down_imgs[3] = images.GetImage("char_down_4");
		right_imgs = new Image[4];
		right_imgs[0] = images.GetImage("char_right_1");
		right_imgs[1] = images.GetImage("char_right_2");
		right_imgs[2] = images.GetImage("char_right_3");
		right_imgs[3] = images.GetImage("char_right_4");
		left_imgs = new Image[4];
		left_imgs[0] = images.GetImage("char_left_1");
		left_imgs[1] = images.GetImage("char_left_2");
		left_imgs[2] = images.GetImage("char_left_3");
		left_imgs[3] = images.GetImage("char_left_4");
		bombs = new ArrayList<Bomb>();
		booms = new ArrayList<Boooom>();
		block_x = x;
		block_y = y;
		px = x*56 + 136;
		py = y*56 + 24;
		width = 56;
		height = 84;
		dir = 2;
		bomblimit = 1;
		bomblong = 1;
		kcal = 0;
		canhit = 0;
		canshoot = 1;
		this.images = images;
		this.audios = audios;
		if (playernum == 1)	this.bombimage = images.GetImage("bomb1");
		else if (playernum == 2)	this.bombimage = images.GetImage("bomb2");
		else if (playernum == 3)	this.bombimage = images.GetImage("bomb3");
		else if (playernum == 4)	this.bombimage = images.GetImage("bomb4");
		this.boomimage = images.GetImage("boooom");
	}
	public void Update(long timeStamp, Block[][] blocks, Player players[], ArrayList<Item> items)
	{
		int[][] arr = new int[216][2];
		int count1 = 0;
		if (5.5/(1+0.0001*kcal) <= 3) vel = 3;
		else if (5.5/(1+0.0001*kcal) >= 7.5) vel = 7.5;
		else vel = 5.5/(1+0.0001*kcal);
		block_x = ((int)px-136)/56;
		block_y = ((int)py-24)/56;
		x = (int)px;
		y = (int)py - 28;
		for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext();)
		{
			Boolean case1 = false; // ∆¯≈∫ ¡Ÿ±‚ø° ∫Œµ˙»˚
			Boolean case2 = false; // ∆¯≈∫¿Ã ∫Œµ˙»˚
			Boolean case3 = false;
			Bomb bomb = (Bomb) iterator.next();
			bomb.block_x = (bomb.x-136)/56;
			bomb.block_y = (bomb.y-24)/56;
			for (Player player : players)
			{
				for (Boooom boom : player.booms)
				{
					if (boom.block_x == bomb.block_x && boom.block_y == bomb.block_y)
					{
						case1 = true;
					}
				}
			}
			//			for (Player player : players)
			//			{
			//				for (Bomb bomb1 : player.bombs)
			//				{
			//					if (bomb1 != bomb && (bomb1.x - 136 + 28)/56 == (bomb.x - 136 +28)/56 && (bomb1.y - 24 + 28)/56 == (bomb.y - 24 + 28)/56)
			//					{
			//						case2 = true;
			//					}
			//				}
			//			}
			for (Player player : players)
			{
				if (player!= this && ((int)player.px - 136 + 28)/56 == (bomb.x - 136 +28)/56 && ((int)player.py - 24 + 28)/56 == (bomb.y - 24 + 28)/56)
				{
					case3 = true;
				}
			}
			if (timeStamp - bomb.spawntime >= 1000 || case1 || case3)
			{
				audios.Loop("pang", 0);
				Random r = new Random();
				int i;
				int count = 0;
				int tempx = (bomb.x - 136 + 28)/56;
				int tempy = (bomb.y - 24 + 28)/56;
				Boooom boom = new Boooom(tempx,tempy,boomimage, timeStamp);
				booms.add(boom);
				for(i = tempy - 1, count = 0;i >= 0 && blocks[tempx][i].status == 0 && count < bomblong;--i, ++count)
				{
					Boooom booom = new Boooom(tempx,i,boomimage, timeStamp);
					booms.add(booom);
				}
				if (i >= 0 && blocks[tempx][i].status == 1 && count < bomblong)
				{
					arr[count1][0] = tempx;
					arr[count1][1] = i;
					++count1;
				}
				for(i = tempx + 1, count = 0;i <= 17 && blocks[i][tempy].status == 0 && count < bomblong;++i, ++count)
				{
					Boooom booom = new Boooom(i,tempy,boomimage, timeStamp);
					booms.add(booom);
				}
				if (i <= 17 && blocks[i][tempy].status == 1 && count < bomblong)
				{
					arr[count1][0] = i;
					arr[count1][1] = tempy;
					++count1;
				}
				for(i = tempy + 1,count = 0;i <= 11 && blocks[tempx][i].status == 0 && count < bomblong;++i, ++count)
				{
					Boooom booom = new Boooom(tempx,i,boomimage, timeStamp);
					booms.add(booom);
				}
				if (i <= 11 && blocks[tempx][i].status == 1 && count < bomblong)
				{
					arr[count1][0] = tempx;
					arr[count1][1] = i;
					++count1;
				}
				for(i = tempx - 1,count = 0;i >= 0 && blocks[i][tempy].status == 0 && count < bomblong;--i, ++count)
				{
					Boooom booom = new Boooom(i,tempy,boomimage, timeStamp);
					booms.add(booom);
				}
				if (i >= 0 && blocks[i][tempy].status == 1 && count < bomblong)
				{
					arr[count1][0] = i;
					arr[count1][1] = tempy;
					++count1;
				}
				iterator.remove();
			}
		}
		for (int i = 0 ;i<count1;++i)
		{
			Random r = new Random();
			int sel = r.nextInt(100);
			if (blocks[arr[i][0]][arr[i][1]].status != 0)
			{
				if (sel >= 0 && sel <= 14)
				{
					Item item = new Item(arr[i][0],arr[i][1],0, images);
					items.add(item);
				}
				else if (sel >= 15 && sel <= 29)
				{
					Item item = new Item(arr[i][0],arr[i][1],1, images);
					items.add(item);
				}
				else if (sel >= 30 && sel <= 32)
				{
					Item item = new Item(arr[i][0],arr[i][1],2, images);
					items.add(item);
				}
			}
			blocks[arr[i][0]][arr[i][1]].ChangeStatus(0);
		}
		for (Iterator<Boooom> iterator = booms.iterator();iterator.hasNext();)
		{
			Boooom boom = (Boooom) iterator.next();
			if (timeStamp- boom.spawntime >= 150)
			{
				iterator.remove();
			}
		}
		for(Bomb bomb : bombs)
		{
			if (bomb.dir == 0)
			{
				if (bomb.y < 24)
				{
					bomb.y = 24;
					bomb.vel_y = 0;
				}
				else if (blocks[bomb.block_x][bomb.block_y].status != 0)
				{
					bomb.y = (bomb.block_y+1) * 56 + 24;
					bomb.vel_y = 0;
					continue;
				}
				for (Player player: players)
				{
					for (Bomb bomb1 : player.bombs)
					{
						if (bomb!=bomb1 && ((int)bomb.x - 136 +28)/56 == ((int)bomb1.x - 136 +28)/56 && ((int)bomb.y - 24 +28)/56 == ((int)bomb1.y - 24 +28)/56 && bomb.vel_y != 0 && bomb1.vel_y >= 0)
						{
							bomb.y = (bomb.block_y+1) * 56 + 24;
							bomb.vel_y = 0;
							break;
						}
					}
				}
			}
			else if (bomb.dir == 1)
			{
				if (bomb.x > 136 + 56 * 17)
				{
					bomb.x = 136 + 56 * 17;
					bomb.vel_x = 0;
				}
				else if (bomb.block_x != 17 && blocks[bomb.block_x+1][bomb.block_y].status != 0)
				{
					bomb.x = bomb.block_x * 56 + 136;
					bomb.vel_x = 0;
					continue;
				}
				for (Player player: players)
				{
					for (Bomb bomb1 : player.bombs)
					{
						if (bomb!=bomb1 && ((int)bomb.x - 136 +28)/56 == ((int)bomb1.x - 136 +28)/56 && ((int)bomb.y - 24 +28)/56 == ((int)bomb1.y - 24 +28)/56 && bomb.vel_x != 0 && bomb1.vel_x <= 0)
						{
							bomb.x = bomb.block_x * 56 + 136;
							bomb.vel_x = 0;
							break;
						}
					}
				}
			}
			else if (bomb.dir == 2)
			{
				if (bomb.y > 24 + 56 * 11)
				{
					bomb.y = 24 + 56 * 11;
					bomb.vel_y = 0;
				}
				else if (bomb.block_y != 11 && blocks[bomb.block_x][bomb.block_y+1].status != 0)
				{
					bomb.y = bomb.block_y * 56 + 24;
					bomb.vel_y = 0;
					continue;
				}
				for (Player player: players)
				{
					for (Bomb bomb1 : player.bombs)
					{
						if (bomb!=bomb1 && ((int)bomb.x - 136 +28)/56 == ((int)bomb1.x - 136 +28)/56 && ((int)bomb.y - 24 +28)/56 == ((int)bomb1.y - 24 +28)/56  && bomb.vel_y != 0 && bomb1.vel_y <= 0)
						{
							bomb.y = bomb.block_y * 56 + 24;
							bomb.vel_y = 0;
							break;
						}
					}
				}
			}
			else if (bomb.dir == 3)
			{
				if (bomb.x < 136)
				{
					bomb.x = 136;
					bomb.vel_x = 0;
				}
				if (blocks[bomb.block_x][bomb.block_y].status != 0)
				{
					bomb.x = (bomb.block_x + 1) * 56 + 136;
					bomb.vel_x = 0;
					continue;
				}
				for (Player player: players)
				{
					for (Bomb bomb1 : player.bombs)
					{
						if (bomb!=bomb1 &&  ((int)bomb.x - 136 +28)/56 == ((int)bomb1.x - 136 +28)/56 && ((int)bomb.y - 24 +28)/56 == ((int)bomb1.y - 24 +28)/56 && bomb.vel_x != 0 && bomb1.vel_x >= 0)
						{
							bomb.x = (bomb.block_x + 1) * 56 + 136;
							bomb.vel_x = 0;
							break;
						}
					}
				}
			}
			bomb.x += (int)bomb.vel_x;
			bomb.y += (int)bomb.vel_y;
		}
		for (Boooom boom : booms)
		{
			for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();)
			{
				Item item = (Item) iterator.next();

				if (boom.block_x == item.block_x && boom.block_y == item.block_y)
				{
					iterator.remove();
				}
			}
		}
		if (canhit == 1 && timeStamp - hittime >= 500)
		{
			canhit = 0;
		}

		if (canshoot == 0)
		{
			if (timeStamp - shoottime >= 0)	canshoot = 1;
		}
		if (canhit == 1)
		{
			image = images.GetImage("eat");
		}
	}
	public void ShootBomb(long timeStamp)
	{
		boolean temp = true;
		for (Bomb bomb : bombs)
		{
			if (((int)px-136 + 28)/56 == ((int)bomb.x-136 + 28)/56 && ((int)py-24 + 28)/56 == ((int)bomb.y-24+28)/56)
			{
				temp = false;
			}
		}
		if (bombs.size() < bomblimit && temp)
		{
			audios.Loop("shoot", 0);
			Bomb bomb = new Bomb(((int)px-136 + 28)/56,((int)py-24 + 28)/56,bombimage, this.dir);
			bomb.spawntime = timeStamp;
			if (this.dir == 0) bomb.vel_y = - 7.5;
			else if (this.dir == 1) bomb.vel_x = 7.5;
			else if (this.dir == 2) bomb.vel_y = 7.5;
			else if (this.dir == 3) bomb.vel_x = - 7.5;
			bombs.add(bomb);
			shoottime = timeStamp;
			canshoot=0;
		}
	}
	public void Hit(long timeStamp,Player player)
	{
		kcal += 200 + player.kcal/50;
		canhit = 1;
		hittime = timeStamp;
	}
}
