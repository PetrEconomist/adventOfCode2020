package days;

import java.util.LinkedList;

import javax.xml.stream.events.EntityDeclaration;

import fileReader.ReadFile;

public class Day12Solution {


	private static final String FILE_NAME = "day12Input.txt";
	private static LinkedList<String> input;
	
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);

		System.out.printf("part1Solution: %f\n", part1Solution());
		System.out.printf("part2Solution: %f\n", part2Solution());
	}

	private static double part1Solution() {
		Ship myShip = new Ship(0, 0, 90.0);
		for(String command : input) {
			myShip.moveShip(command);
		}
		return myShip.getManhattanDistance();
	}
	
	private static double part2Solution() {
		Ship myShip = new Ship(0, 0, 0, 10, 1);
		for(String command : input) {
			myShip.moveWaypoint(command);
		}
		return myShip.getManhattanDistance();
	}
	
	
	private static class Ship{
		private static final char NORTH = 'N';
		private static final char SOUTH = 'S';
		private static final char EAST = 'E';
		private static final char WEST = 'W';
		private static final char RIGHT = 'R';
		private static final char LEFT = 'L';
		private static final char FORWARD = 'F';

		
		//North = Y plus
		//South = Y minus
		//West = X minus
		//East = X plus;
		//coordinates (positions)
		private double posX, posY;
		private double wayPointX, wayPointY;
		//direction in degrees
		private double azimuth; //(0°=North)
		
		Ship(double posX, double posY, double azimuth, double wayPointX, double wayPointY){
			this.posX = posX;
			this.posY = posY;
			this.azimuth = azimuth;
			this.wayPointX = wayPointX;
			this.wayPointY = wayPointY;
		}
		
		Ship(double posX, double posY, double azimuth){
			this(posX, posY, azimuth, 0, 0);
		}
		
		public void moveShip(String command) {
			char commandCode = command.charAt(0);
			int commandValue = Integer.parseInt(command.substring(1));
			
			switch(commandCode) {
				case NORTH: posY += commandValue;
							break;
				case SOUTH: posY -= commandValue;
							break;
				case EAST: 	posX += commandValue;
							break;
				case WEST: 	posX -= commandValue;
							break;
				case FORWARD: posX += commandValue * Math.round(Math.sin(Math.toRadians(azimuth)));
								posY += commandValue * Math.round(Math.cos(Math.toRadians(azimuth)));
								break;
				case LEFT:	azimuth -= commandValue;
							break;
				case RIGHT: azimuth += commandValue;
							break;
			}
		}
		
		public void moveWaypoint(String command) {
			char commandCode = command.charAt(0);
			int commandValue = Integer.parseInt(command.substring(1));
			
			switch(commandCode) {
				case NORTH: wayPointY += commandValue;
							break;
				case SOUTH: wayPointY -= commandValue;
							break;
				case EAST: 	wayPointX += commandValue;
							break;
				case WEST: 	wayPointX -= commandValue;
							break;
				case FORWARD: 	posX += (wayPointX * commandValue);
								posY += (wayPointY * commandValue);
								break;
				case LEFT:	rotateWaypoint(commandValue);
							break;

				case RIGHT: rotateWaypoint(-commandValue);
							break;
			}
		}
		
		private void rotateWaypoint(double degrees) {
			//rotates counterclockwise
			double sin = Math.round(Math.sin(Math.toRadians(degrees)));
			double cos = Math.round(Math.cos(Math.toRadians(degrees)));
			double wayPointXNew = wayPointX * cos - wayPointY * sin;
			double wayPointYNew = wayPointX * sin + wayPointY * cos;
			wayPointX = wayPointXNew;
			wayPointY = wayPointYNew;
		}
		
		private double getX() {
			return posX;
		}
		
		private double getY() {
			return posY;
		}
		
		public double getManhattanDistance() {
			return Math.abs(getX()) + Math.abs(getY());
		}

		

		
	}
	

}
