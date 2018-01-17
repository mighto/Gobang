package com.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * 功能描述: 作者: mistaker 邮箱: 2029635554@qq.com 创建时间: 2017年12月25日
 *************************************
 */

public class ChessFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String[] strsize = { "标准棋盘", "改进棋盘", "扩大棋盘" };
	private String[] strmode = { "人机对战", "人人对战" };
	public static boolean iscomputer = true, checkcomputer = true;
	private int width, height;
	private ChessModel cm;
	private MainPanel mp;

	public ChessFrame() {
		this.setTitle("五子棋游戏");
		cm = new ChessModel(1);
		mp = new MainPanel(cm);
		Container con = this.getContentPane();
		con.add(mp, "Center");
		this.setResizable(false);
		this.addWindowListener(new ChessWindowEvent());
		MapSize(14, 14);
		JMenuBar mbar = new JMenuBar();
		this.setJMenuBar(mbar);
		JMenu gameMenu = new JMenu("游戏");
		mbar.add(makeMenu(gameMenu, new Object[] { "开局", null, "棋盘", null, "模式", null, "退出" }, this));
		JMenu lookMenu = new JMenu("外观");
		mbar.add(makeMenu(lookMenu, new Object[] { "类型一", "类型二", "类型三" }, this));
		JMenu helpMenu = new JMenu("版本");
		mbar.add(makeMenu(helpMenu, new Object[] { "关于" }, this));
	}

	public JMenu makeMenu(Object parent, Object items[], Object target) {
		JMenu m = null;
		if (parent instanceof JMenu)
			m = (JMenu) parent;
		else if (parent instanceof String)
			m = new JMenu((String) parent);
		else
			return null;
		for (int i = 0; i < items.length; i++)
			if (items[i] == null)
				m.addSeparator();
			else if (items[i] == "棋盘") {
				JMenu jm = new JMenu("棋盘");
				ButtonGroup group = new ButtonGroup();
				JRadioButtonMenuItem rmenu;
				for (int j = 0; j < strsize.length; j++) {
					rmenu = makeRadioButtonMenuItem(strsize[j], target);
					if (j == 0)
						rmenu.setSelected(true);
					jm.add(rmenu);
					group.add(rmenu);
				}
				m.add(jm);
			} else if (items[i] == "模式") {
				JMenu jm = new JMenu("模式");
				ButtonGroup group = new ButtonGroup();
				JRadioButtonMenuItem rmenu;
				for (int h = 0; h < strmode.length; h++) {
					rmenu = makeRadioButtonMenuItem(strmode[h], target);
					if (h == 0)
						rmenu.setSelected(true);
					jm.add(rmenu);
					group.add(rmenu);
				}
				m.add(jm);
			} else
				m.add(makeMenuItem(items[i], target));
		return m;
	}

	public JMenuItem makeMenuItem(Object item, Object target) {
		JMenuItem r = null;
		if (item instanceof String)
			r = new JMenuItem((String) item);
		else if (item instanceof JMenuItem)
			r = (JMenuItem) item;
		else
			return null;
		if (target instanceof ActionListener)
			r.addActionListener((ActionListener) target);
		return r;
	}

	// 构造五子棋游戏的单选按钮式菜单项
	public JRadioButtonMenuItem makeRadioButtonMenuItem(Object item, Object target) {
		JRadioButtonMenuItem r = null;
		if (item instanceof String)
			r = new JRadioButtonMenuItem((String) item);
		else if (item instanceof JRadioButtonMenuItem)
			r = (JRadioButtonMenuItem) item;
		else
			return null;
		if (target instanceof ActionListener)
			r.addActionListener((ActionListener) target);
		return r;
	}

	public void MapSize(int w, int h) {
		setSize(w * 24, h * 27);
		if (ChessFrame.checkcomputer)
			ChessFrame.iscomputer = true;
		else
			ChessFrame.iscomputer = false;
		mp.setModel(cm);
		mp.repaint();
	}

	public boolean getiscomputer() {
		return ChessFrame.iscomputer;
	}

	public void restart() {
		int modeChess = cm.getModeChess();
		if (modeChess <= 3 && modeChess >= 0) {
			cm = new ChessModel(modeChess);
			MapSize(cm.getWidth(), cm.getHeight());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String arg = e.getActionCommand();
		try {
			if (arg.equals("类型三"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			else if (arg.equals("类型二"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			else
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception ee) {
		}
		if (arg.equals("标准棋盘")) {
			this.width = 14;
			this.height = 14;
			cm = new ChessModel(1);
			MapSize(this.width, this.height);
			SwingUtilities.updateComponentTreeUI(this);
		}
		if (arg.equals("改进棋盘")) {
			this.width = 18;
			this.height = 18;
			cm = new ChessModel(2);
			MapSize(this.width, this.height);
			SwingUtilities.updateComponentTreeUI(this);
		}
		if (arg.equals("扩大棋盘")) {
			this.width = 22;
			this.height = 22;
			cm = new ChessModel(3);
			MapSize(this.width, this.height);
			SwingUtilities.updateComponentTreeUI(this);
		}
		if (arg.equals("人机对战")) {
			ChessFrame.checkcomputer = true;
			ChessFrame.iscomputer = true;
			cm = new ChessModel(cm.getModeChess());
			MapSize(cm.getWidth(), cm.getHeight());
			SwingUtilities.updateComponentTreeUI(this);
		}
		if (arg.equals("人人对战")) {
			ChessFrame.checkcomputer = false;
			ChessFrame.iscomputer = false;
			cm = new ChessModel(cm.getModeChess());
			MapSize(cm.getWidth(), cm.getHeight());
			SwingUtilities.updateComponentTreeUI(this);
		}
		if (arg.equals("开局")) {
			restart();
		}
		if (arg.equals("关于"))
			JOptionPane.showMessageDialog(null, "第一版", "版本", JOptionPane.PLAIN_MESSAGE);
		if (arg.equals("退出"))
			System.exit(0);
	}
}

class ChessModel{
	private int width,height,modeChess;
	private int x=0,y=0;
	private int[][] arrMapShow;
	private boolean isOdd,isExist;
	
	public ChessModel() {}
	
	public ChessModel(int modeChess){
	   this.isOdd=true;
	   if(modeChess == 1){
	    PanelInit(14, 14, modeChess);
	   }
	   if(modeChess == 2){
	    PanelInit(18, 18, modeChess);
	   }
	   if(modeChess == 3){
	    PanelInit(22, 22, modeChess);
	   }
	}
	
//	按照棋盘模式构建棋盘大小
	private void PanelInit(int width, int height, int modeChess){
	   this.width = width;
	   this.height = height;
	   this.modeChess = modeChess;
	   arrMapShow = new int[width+1][height+1];
	   for(int i = 0; i <= width; i++){
	    for(int j = 0; j <= height; j++){
	     arrMapShow[i][j] = -1;
	    }
	   }
	}
	
//	获取是否交换棋手的标识符
	public boolean getisOdd(){
	   return this.isOdd;
	}

//	设置交换棋手的标识符
	public void setisOdd(boolean isodd){
	   if(isodd)
	    this.isOdd=true;
	   else
	    this.isOdd=false;
	}

//	获取某棋盘方格是否有棋子的标识值
	public boolean getisExist(){
	   return this.isExist;
	}

//	获取棋盘宽度
	public int getWidth(){
	   return this.width;
	}

//	获取棋盘高度
	public int getHeight(){
	   return this.height;
	}

//	获取棋盘模式
	public int getModeChess(){
	   return this.modeChess;
	}

//	获取棋盘方格上棋子的信息
	public int[][] getarrMapShow(){
	   return arrMapShow;
	}
	
	
//	判断下子的横向、纵向坐标是否越界
	private boolean badxy(int x, int y){
	   if(x >= width+20 || x < 0)
	    return true;
	   return y >= height+20 || y < 0;
	}

//	计算棋盘上某一方格上八个方向棋子的最大值，
//	这八个方向分别是：左、右、上、下、左上、左下、右上、右下
	public boolean chessExist(int i,int j){
	   if(this.arrMapShow[i][j]==1 || this.arrMapShow[i][j]==2)
	    return true;
	   return false;
	}

//	判断该坐标位置是否可下棋子
	public void readyplay(int x,int y){
	   if(badxy(x,y))
	    return;
	   if (chessExist(x,y))
	    return;
	   this.arrMapShow[x][y]=3;
	}

//	在该坐标位置下棋子
	public void play(int x,int y){
	   if(badxy(x,y))
	    return;
	   if(chessExist(x,y)){
	    this.isExist=true;
	    return;
	   }else
	    this.isExist=false;
	   if(getisOdd()){
	    setisOdd(false);
	   this.arrMapShow[x][y]=1;
	   }else{
	    setisOdd(true);
	    this.arrMapShow[x][y]=2;
	   }
	}
	
//	计算机走棋
//	说明：用穷举法判断每一个坐标点的四个方向的的最大棋子数，
//	最后得出棋子数最大值的坐标，下子
	public void computerDo(int width,int height){
	   int max_black,max_white,max_temp,max=0;
	   setisOdd(true);
	   System.out.println("计算机走棋 ...");
	   for(int i = 0; i <= width; i++){
	for(int j = 0; j <= height; j++){
//	算法判断是否下子
	     if(!chessExist(i,j)){
//	判断白子的最大值
	      max_white=checkMax(i,j,2);
//	   判断黑子的最大值
	      max_black=checkMax(i,j,1);
	      max_temp=Math.max(max_white,max_black);
	      if(max_temp>max){
	       max=max_temp;
	       this.x=i;
	       this.y=j;
	      }
	     }
	    }
	   }
	   setX(this.x);
	   setY(this.y);
	   this.arrMapShow[this.x][this.y]=2;
	}

//	记录电脑下子后的横向坐标
	public void setX(int x){
	   this.x=x;
	}

//	记录电脑下子后的纵向坐标
	public void setY(int y){
	   this.y=y;
	}

//	获取电脑下子的横向坐标
	public int getX(){
	   return this.x;
	}

//	获取电脑下子的纵向坐标
	public int getY(){
	   return this.y;
	}
	
	
//	计算棋盘上某一方格上八个方向棋子的最大值，
//	这八个方向分别是：左、右、上、下、左上、左下、右上、右下
	public int checkMax(int x, int y,int black_or_white){
	   int num=0,max_num,max_temp=0;
	   int x_temp=x,y_temp=y;
	   int x_temp1=x_temp,y_temp1=y_temp;
//	   判断右边
	   for(int i=1;i<5;i++){
	    x_temp1+=1;
	    if(x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
//	    判断左边
	   x_temp1=x_temp;
	   for(int i=1;i<5;i++){
	    x_temp1-=1;
	    if(x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num<5)
	    max_temp=num;

//	    判断上面
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=0;
	   for(int i=1;i<5;i++){
	    y_temp1-=1;
	    if(y_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
//	    判断下面
	   y_temp1=y_temp;
	   for(int i=1;i<5;i++){
	    y_temp1+=1;
	    if(y_temp1>this.height)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num>max_temp&&num<5)
	    max_temp=num;

//	    判断左上方
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=0;
	   for(int i=1;i<5;i++){
	    x_temp1-=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
//	    判断右下方
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<5;i++){
	    x_temp1+=1;
	    y_temp1+=1;
	    if(y_temp1>this.height || x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num>max_temp&&num<5)
	    max_temp=num;

//	    判断右上方
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=0;
	   for(int i=1;i<5;i++){
	    x_temp1+=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
//	    判断左下方
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<5;i++){
	    x_temp1-=1;
	    y_temp1+=1;
	    if(y_temp1>this.height || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
	     num++;
	    else
	     break;
	   }
	   if(num>max_temp&&num<5)
	    max_temp=num;
	   max_num=max_temp;
	   return max_num;
	}
	
//	判断胜负
	public boolean judgeSuccess(int x,int y,boolean isodd){
	   int num=1;
	   int arrvalue;
	   int x_temp=x,y_temp=y;
	   if(isodd)
	    arrvalue=2;
	   else
	    arrvalue=1;
	   int x_temp1=x_temp,y_temp1=y_temp;
//	   判断右边胜负
	   for(int i=1;i<6;i++){
	    x_temp1+=1;
	    if(x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
//	   判断左边胜负
	   x_temp1=x_temp;
	   for(int i=1;i<6;i++){
	    x_temp1-=1;
	    if(x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;

//	   判断上方胜负
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=1;
	   for(int i=1;i<6;i++){
	    y_temp1-=1;
	    if(y_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
//	   判断下方胜负
	   y_temp1=y_temp;
	   for(int i=1;i<6;i++){
	    y_temp1+=1;
	    if(y_temp1>this.height)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;
//	   判断左上胜负
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=1;
	   for(int i=1;i<6;i++){
	    x_temp1-=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
//	    判断右下胜负
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<6;i++){
	   x_temp1+=1;
	   y_temp1+=1;
	   if(y_temp1>this.height || x_temp1>this.width)
	    break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;

//	    判断右上胜负
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   num=1;
	   for(int i=1;i<6;i++){
	    x_temp1+=1;
	    y_temp1-=1;
	    if(y_temp1<0 || x_temp1>this.width)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
//	    判断左下胜负
	   x_temp1=x_temp;
	   y_temp1=y_temp;
	   for(int i=1;i<6;i++){
	    x_temp1-=1;
	    y_temp1+=1;
	    if(y_temp1>this.height || x_temp1<0)
	     break;
	    if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
	     num++;
	    else
	     break;
	   }
	   if(num==5)
	    return true;
	   return false;
	}
	
//	赢棋后的提示
	public void showSuccess(JPanel jp){
	   JOptionPane.showMessageDialog(jp,"你赢了","结果",
	    JOptionPane.INFORMATION_MESSAGE);
	}

//	输棋后的提示
	public void showDefeat(JPanel jp){
	   JOptionPane.showMessageDialog(jp,"你输了","结果",
	    JOptionPane.INFORMATION_MESSAGE);
	}
	
}

class MainPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	// 设定棋盘的宽度和高度
	private int width, height;
	private ChessModel cm;

	// 根据棋盘模式设定面板的大小
	MainPanel(ChessModel mm) {
		cm = mm;
		width = cm.getWidth();
		height = cm.getHeight();
		addMouseListener(this);
	}

	// 根据棋盘模式设定棋盘的宽度和高度
	public void setModel(ChessModel mm) {
		cm = mm;
		width = cm.getWidth();
		height = cm.getHeight();
	}

	// 根据坐标计算出棋盘方格棋子的信息（如白子还是黑子），
	// 然后调用draw方法在棋盘上画出相应的棋子
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int j = 0; j <= height; j++) {
			for (int i = 0; i <= width; i++) {
				int v = cm.getarrMapShow()[i][j];
				draw(g, i, j, v);
			}
		}
	}

	// 根据提供的棋子信息（颜色、坐标）画棋子
	public void draw(Graphics g, int i, int j, int v) {
		int x = 20 * i + 20;
		int y = 20 * j + 20;
		// 画棋盘
		if (i != width && j != height) {
			g.setColor(Color.darkGray);
			g.drawRect(x, y, 20, 20);
		}
		// 画黑色棋子
		if (v == 1) {
			g.setColor(Color.gray);
			g.drawOval(x - 8, y - 8, 16, 16);
			g.setColor(Color.black);
			g.fillOval(x - 8, y - 8, 16, 16);
		}
		// 画白色棋子
		if (v == 2) {
			g.setColor(Color.gray);
			g.drawOval(x - 8, y - 8, 16, 16);
			g.setColor(Color.white);
			g.fillOval(x - 8, y - 8, 16, 16);
		}
		if (v == 3) {
			g.setColor(Color.cyan);
			g.drawOval(x - 8, y - 8, 16, 16);
		}
	}

	// 响应鼠标的点击事件，根据鼠标的点击来下棋，
	// 根据下棋判断胜负等
	public void mousePressed(MouseEvent evt) {
		int x = (evt.getX() - 10) / 20;
		int y = (evt.getY() - 10) / 20;
		System.out.println(x + " " + y);
		if (evt.getModifiers() == MouseEvent.BUTTON1_MASK) {
			cm.play(x, y);
			System.out.println(cm.getisOdd() + " " + cm.getarrMapShow()[x][y]);
			repaint();
			if (cm.judgeSuccess(x, y, cm.getisOdd())) {
				cm.showSuccess(this);
				evt.consume();
				ChessFrame.iscomputer = false;
			}
			// 判断是否为人机对弈
			if (ChessFrame.iscomputer && !cm.getisExist()) {
				cm.computerDo(cm.getWidth(), cm.getHeight());
				repaint();
				if (cm.judgeSuccess(cm.getX(), cm.getY(), cm.getisOdd())) {
					cm.showDefeat(this);
					evt.consume();
				}
			}
		}
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent mouseevt) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent evt) {
	}

	// 响应鼠标的拖动事件
	public void mouseMoved(MouseEvent moveevt) {
		int x = (moveevt.getX() - 10) / 20;
		int y = (moveevt.getY() - 10) / 20;
		cm.readyplay(x, y);
		repaint();
	}
}

// 响应退出窗口
class ChessWindowEvent extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	ChessWindowEvent() {
	}
}
