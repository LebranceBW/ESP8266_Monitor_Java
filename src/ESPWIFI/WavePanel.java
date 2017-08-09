package ESPWIFI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

public class WavePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private  int baseX = 10;
	private  int baseY = 266;
	private final int xLength = 700;
	private final int yLength = 256;
	private Image iBuffer; 
	private Graphics gBuffer;
	private Queue<Integer> Q = new LinkedList<Integer>(); 
	public WavePanel()
	{
		super();
	}
	
	public void paint(Graphics g)
	{
		if (iBuffer == null) {
			iBuffer = createImage(getWidth(), getHeight());
			gBuffer=iBuffer.getGraphics(); 
        }
		baseX = (int)	(0.1*getWidth());
		baseY = (int) (0.9 * getHeight());
		gBuffer.setColor(Color.BLACK);
		gBuffer.drawLine(baseX, baseY, baseX, baseY-yLength);
		gBuffer.drawLine(baseX, baseY, baseX+xLength, baseY);
		gBuffer.drawRect(0, 0, getWidth(), getHeight());
		int x = 0;
		for(Integer i : Q)
		{
			gBuffer.drawLine(x + baseX,baseY-i,x+baseX,baseY-i);
			x++;
		}
		g.drawImage(iBuffer, 0, 0, this);
	}
	
	public void enterQ(int data)
	{
		if(Q.size() == xLength)
			Q.remove();
		Q.add(data);
		updateUI();
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
}
