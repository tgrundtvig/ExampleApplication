package exampleapplication;

import applicationapi.Application;
import applicationapi.Device;
import applicationapi.graphics.Canvas;
import applicationapi.graphics.Color;
import applicationapi.graphics.Screen;
import applicationapi.graphics.Sprite;
import applicationapi.graphics.SpriteBuilder;
import applicationapi.graphics.SpriteFactory;
import applicationapi.input.keyboard.Key;
import applicationapi.input.keyboard.KeyPressedEvent;
import applicationapi.input.keyboard.KeyReleasedEvent;
import applicationapi.input.keyboard.KeyboardListener;
import applicationapi.input.mouse.MouseButton;
import applicationapi.input.mouse.MousePressedEvent;
import applicationapi.input.mouse.MouseListener;
import applicationapi.input.mouse.MouseMovedEvent;
import applicationapi.input.mouse.MouseReleasedEvent;
import applicationapi.input.mouse.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tog
 */
public class ExampleApplication implements Application, KeyboardListener, MouseListener
{
    private boolean keyLeftPressed;
    private boolean keyRightPressed;
    private boolean keyUpPressed;
    private boolean keyDownPressed;
    private boolean mouseDown;
    private Sprite ball;
    private float fPosX;
    private float fPosY;
    private int width;
    private int height;
    private float movePrMilliSecond;
    private long lastTime;
    private Sprite dot;
    private List<Position> dots;
    private int mouseX;
    private int mouseY;
    
    @Override
    public boolean initialize(Device dev)
    {
        this.fPosX = 0.5f;
        this.fPosY = 0.5f;
        this.keyLeftPressed = false;
        this.keyRightPressed = false;
        this.keyUpPressed = false;
        this.keyDownPressed = false;
        this.mouseDown = false;
        this.mouseX = 0;
        this.mouseY = 0;
        this.dots = new ArrayList<>();
        dev.getKeyboard().addKeyboardListener(this);
        dev.getMouse().addMouseListener(this);
        Screen screen = dev.getScreen();
        if(screen == null) return false;
        this.width = screen.getWidth();
        this.height = screen.getHeight();
        this.movePrMilliSecond = 1.0f / 5000.0f;
        
        int r = height / 24;
        int r2 = r*r;
        int s = r+r+1;
        SpriteFactory sf = screen.getSpriteFactory();
        SpriteBuilder bld = sf.newSprite(s, s);
        Color black = sf.newColor(0, 0, 0, 1);
        Color white = sf.newColor(1, 1, 1, 0);
        for(int x = 0; x < s; ++x)
        {
            for(int y = 0; y < s; ++y)
            {
                int dx = x - r;
                int dy = y - r;
                int dx2 = dx * dx;
                int dy2 = dy * dy;
                int dist2 = dx2 + dy2;
                if(dist2 >= r2)
                {
                    bld.setPixel(x, y, white);
                }
                else
                {
                    bld.setPixel(x, y, black);
                }
            }
        }
        bld.setAnchor(r, r);
        ball = bld.build();
        int dotSize = 10;
        bld = sf.newSprite(dotSize, dotSize);
        for(int y = 0; y < dotSize; ++y)
        {
            for(int x = 0; x < dotSize; ++x)
            {
                bld.setPixel(x, y, black);
            }
        }
        bld.setAnchor(dotSize/2, dotSize/2);
        dot = bld.build();
        return true;
    }

    @Override
    public boolean update(long time)
    {
        long deltaTime = time - lastTime;
        lastTime = time;
        float fdx; 
        if(keyLeftPressed && !keyRightPressed) fdx = deltaTime * -movePrMilliSecond;
        else if(keyRightPressed && !keyLeftPressed) fdx = deltaTime * movePrMilliSecond;
        else fdx = 0.0f;
        
        float fdy;
        if(keyUpPressed && !keyDownPressed) fdy = deltaTime * -movePrMilliSecond;
        else if(keyDownPressed && !keyUpPressed) fdy = deltaTime * movePrMilliSecond;
        else fdy = 0.0f;
        
        fPosX += fdx;
        fPosY += fdy;
        return (fPosX >= 0.0f && fPosX <= 1.0f && fPosY >= 0.0f && fPosY <= 1.0f); 
    }

    @Override
    public void draw(Canvas canvas)
    {
        int posX = (int) (fPosX * width);
        int posY = (int) (fPosY * height);
        canvas.drawSprite(posX, posY, ball);
        
        for(Position p : dots)
        {
            canvas.drawSprite(p.x, p.y, dot);
        }
        canvas.drawSprite(mouseX, mouseY, dot);
    }

    @Override
    public void destroy()
    {
        //Do nothing
    }

    @Override
    public void onKeyPressed(KeyPressedEvent e)
    {
        if(e.getKey() == Key.VK_LEFT) keyLeftPressed = true;
        else if(e.getKey() == Key.VK_RIGHT) keyRightPressed = true;
        else if(e.getKey() == Key.VK_UP) keyUpPressed = true;
        else if(e.getKey() == Key.VK_DOWN) keyDownPressed = true;
    }

    @Override
    public void onKeyReleased(KeyReleasedEvent e)
    {
        if(e.getKey() == Key.VK_LEFT) keyLeftPressed = false;
        else if(e.getKey() == Key.VK_RIGHT) keyRightPressed = false;
        else if(e.getKey() == Key.VK_UP) keyUpPressed = false;
        else if(e.getKey() == Key.VK_DOWN) keyDownPressed = false;
    }

    @Override
    public void onMouseMoved(MouseMovedEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
        if(mouseDown)
        {
            dots.add(new Position(mouseX, mouseY));
        }
    }

    @Override
    public void onMousePressed(MousePressedEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
        if(e.getButton() == MouseButton.RIGHT)
        {
            mouseDown = true;
            dots.add(new Position(mouseX, mouseY));
        }
    }

    @Override
    public void onMouseReleased(MouseReleasedEvent e)
    {
        if(e.getButton() == MouseButton.RIGHT)
        {
            mouseDown = false;
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void onMouseWheel(MouseWheelEvent e)
    {
        //Do nothing
    }

    private static class Position
    {
        public final int x;
        public final int y;

        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    
}
