package Main;

import Controller.*;
import Model.*;
import View.*;

public class Main {
	public static void main(String[] args) {
		  // Assemble all the pieces of the MVC
		  View v = new View();
		  model m = new model(660, 3, 3, 2, 3);
		  Controller c = new Controller(v, m);
		  c.initController();
		 }
}
