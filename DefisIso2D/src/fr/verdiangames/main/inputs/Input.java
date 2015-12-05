package fr.verdiangames.main.inputs;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, FocusListener {
	
	public boolean[] keys = new boolean[256];
	public boolean[] buttons = new boolean[10];
	
	public boolean focused;
	public int x, y;
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;		
	}

	public void focusGained(FocusEvent arg0) {
		focused = true;
	}

	public void focusLost(FocusEvent arg0) {
		focused = false;
	}
}