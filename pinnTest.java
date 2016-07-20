
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito; 
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.*;

public class pinnTest {
@SuppressWarnings("unchecked")
	
//	@Mock
//	GameOfLife mock = Mockito.mock(GameOfLife.class);
//	
//	@Before
//	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(mock);		
//	}
//	
//	@After
//	public void tearDown() throws Exception {
//		// no necessary right here
//	}	
	
	/*
	 * converToInt test in mainPanel class
	 * the returned number should always be the same
	 * as the input number
	 */

	public int convertToIntOriginal(int x) { //show the original code and compare the result
		int c = 0;
		String padding = "0";
		while (c < 1000) {
		    String l = new String("0");
		    padding += l;
		    c++;
		}
		
		String n = padding + String.valueOf(x);
		
		int q = Integer.parseInt(n);
		return q;
	    }
	
	
	//test the normal integer
	@Test
	public void converToIntTest1(){
		int size = 9;
		MainPanel pm = new MainPanel(size);
		int res = pm.convertToInt(8);
		assertEquals(res, 8);
	}
	
	//test the max size
	@Test
	public void converToIntTest2(){
		int size = 9;
		MainPanel pm = new MainPanel(size);
		int res = pm.convertToInt(999);
		int res1 = convertToIntOriginal(999);
		assertEquals(res, res1);
	}
	
	
	//the negative input number such as -1 should not be passed into the
	//original method becasue for string like 000000000...000-1, it can not be
	// convert to the integer.
	@Test
	public void converToIntTest3(){
		int size = 9;
		MainPanel pm = new MainPanel(size);
		int res = pm.convertToInt(-1);
//		
		int c = 0;
		String padding = "0";
		while (c < 10) {
		    String l = new String("0");
		    padding += l;
		    c++;
		}
		String n = padding + String.valueOf(-1);
		System.out.println(n);
//		int q = Integer.parseInt(n);
//		System.out.println(q)
		try{
			convertToIntOriginal(-1);
		}catch(NumberFormatException e){
			 return;
		}
		
	}
	
	
	
	/* toString() method in Cell class
	 * test three cases to ensure that 
	 * nothing has been changed
	 */
	
	//although the initiated cell object does not pass the true/false into the constructor
	// if we mannually set the setAlive(true), it still should be X
	@Test
	public void toStringTest1(){
		Cell test = new Cell();
		test.setAlive(true);
		assertTrue(test.toString() == "X");
	}
	
	//test new constructed object which has been passed true
	//it should automatically equal to X when called the toString method();
	@Test
	public void toStringTest2(){
		Cell test = new Cell(true);
		assertTrue(test.toString() == "X");
	}
	
	//test new constructed object which has been passed false
	//it should automatically equal to . when called the toString method(); 
	@Test
	public void toStringTest3(){
		Cell test = new Cell(false);
		assertFalse(test.toString() == "X");
		assertTrue(test.toString() == ".");
	}
	
	/*
	 * RunContinuouslyTest
	 * test the runContinuously() method
	 * this method is hard to test using unit testing to some degree
	 * so just use some mannual ways.
	 */
	
	//the original runContinuously() method:
	 public long runContinuous1() {
		 long startTime = System.currentTimeMillis();
			boolean _running = true;
			int _r = 100;
			int _maxCount = 500;
			int _size = 15;
			//while (_running) {
			    System.out.println("Running...");
			    int origR = _r; // 1000
			    try {
				Thread.sleep(20);
			    } catch (InterruptedException iex) { }
			   
			    for (int j=0; j < _maxCount; j++) { //10000
			    	_r += (j % _size) % _maxCount;
			    	_r += _maxCount;
			    }
			    _r = origR;
			    MainPanel m = new MainPanel(_size);
			    m.backup();
			    m.calculateNextIteration();
			//}
		  long endTime = System.currentTimeMillis();
		  long time = endTime - startTime ;
		  return time;
	  }
	 
	//i just compared the revised version's time with its original
	//but i removed the while loop to use the currentTimeMillils to 
	//calculate. otherwise the while loop will not stop. 
	@Test
	public void runContinuouslyTest(){
		int _size = 15;
		long startTime = System.currentTimeMillis();
		MainPanel m = new MainPanel(_size);
			System.out.println("Running...");  	//this 3 lines code 
			m.backup();							//are revised runContinuosly()
			m.calculateNextIteration();			//without the while loop
		long endTime = System.currentTimeMillis();
		long time1 = endTime - startTime;
		assertTrue(time1 < runContinuous1());
	}
	
	// manually test the new version will also 
	// pringtout running..., calculating... and displaying... 
	@Test
	public void runContinuouslyTest2(){
		int i = 0;
		boolean _running = true;
		MainPanel m = new MainPanel(15);
		while (_running && i < 5) {  //printout 5 times.
		    System.out.println("Running...");
		    //int origR = _r; // 1000
//		    try {
//			Thread.sleep(20);
//		    } catch (InterruptedException iex) { }
		    /*
		     * the loop below is useless
		     * no matter what _r it got,
		     * it will remain value of origR
		     */
//		    for (int j=0; j < _maxCount; j++) { //10000
//		    	_r += (j % _size) % _maxCount;
//		    	_r += _maxCount;
//		    }
		    //_r = origR;
		    m.backup();
		    m.calculateNextIteration();
		    i++; 
		}
	}
	
	//test: 1st: set the new created 15x15 cells true,
	// 2: run the runContinous() method without the while loop
	// 3: as what MainPanel did, write a new convertToBoolean method, and
	// see if the getAlive equals to false (which already been set to true), 
	// if yes, fail;
	@Test
	public void runContinuouslyTest3(){
		MainPanel m = new MainPanel(15);
		Cell[][] cell = new Cell[15][15];
        for(int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = new Cell(true);
            }
        }
        runContinuouslyTest2();
        boolean[][] boo = m.convertToBoolean(m.getCells());
        for(int i = 0; i < boo.length; i++){
            for(int j = 0; j < boo[i].length; j++){
                if(cell[i][j].getAlive() == false) fail();
            }
        }
	}
}
