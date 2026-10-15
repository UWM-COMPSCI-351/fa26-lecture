import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import edu.uwm.cs351.Queue;
import junit.framework.TestCase;



public class TestQueue extends TestCase {

        Queue<Integer> queue;

        @Override
        public void setUp(){
                try {
                        assert 1/0 == 42 : "OK";
                        System.err.println("Assertions must be enabled to use this test case.");
                        System.err.println("In Eclipse: add -ea in the VM Arguments box under Run>Run Configurations>Arguments");
                        assertFalse("Assertions must be -ea enabled in the Run Configuration>Arguments>VM Arguments",true);
                } catch (ArithmeticException ex) {
                        // GOOD
                }
                queue = new Queue<>();
        }

        public void test() {
        	queue = new Queue<Integer>();
        	assertTrue(queue.isEmpty());
        	assertEquals(0,queue.size());
        }
        
        public void test00() {
                queue = new Queue<Integer>();
                assertTrue(queue.isEmpty());
                queue.enqueue(16);
                assertEquals(16, (int) queue.front());
        }

        public void test02() {
                queue = new Queue<Integer>();
                queue.enqueue(78);
                queue.enqueue(28);
                assertEquals(78, (int) queue.front());
        }
        
        public void test03() {
                queue.enqueue(42);
                assertFalse(queue.isEmpty());
                assertEquals(42, (int) queue.front());
                assertEquals(42, (int) queue.dequeue());
                assertTrue(queue.isEmpty());
        }
        
        public void test04() {
                queue.enqueue(2);
                queue.enqueue(53);
                assertEquals(2, (int) queue.front());
                
                queue.dequeue();
                queue.enqueue(9);
                assertEquals(53, (int) queue.dequeue());
                assertEquals(9, (int) queue.dequeue());
        }

        public void test05() {
                class Animal {
                        private int agitation = 0;
                        private String sound;
                        public Animal(String sound) { this.sound = sound;}
                        public String poke() { return ++agitation >= 3 ? sound.toUpperCase() +"!!!" : sound; }
                }
                Queue<Animal> animals = new Queue<>();

                animals.enqueue(new Animal("Moo"));
                animals.enqueue(new Animal("Ruff"));
                assertEquals("Moo", animals.dequeue().poke());
                
                animals.enqueue(new Animal("Baa"));
                assertEquals("Ruff", animals.dequeue().poke());
                assertEquals("Baa", animals.front().poke());
                assertEquals("Baa", animals.front().poke());
                assertEquals("BAA!!!", animals.dequeue().poke());
                assertTrue(animals.isEmpty());
        }
        
        public void test08() {
                Queue<String> queue = new Queue<String>();
                queue.enqueue("Catelyn");
                queue.enqueue("Bill");
                queue.enqueue("Xander");
                queue.enqueue("Barb");
                
                assertEquals("Catelyn", queue.front());
                assertEquals("Catelyn", queue.dequeue());
                assertEquals("Bill", queue.front());
                assertEquals("Bill", queue.dequeue());
                assertEquals("Xander", queue.dequeue());
                assertEquals("Barb", queue.dequeue());
        }
        
        public void test09() {
                Queue<Queue<String>> whoa = new Queue<>();
                whoa.enqueue(new Queue<>());
                whoa.front().enqueue("alpha");
                
                whoa.enqueue(new Queue<>());
                whoa.front().enqueue("bravo");
                
                whoa.enqueue(new Queue<>());
                whoa.front().enqueue("charlie");

                assertEquals("alpha", whoa.front().dequeue());
                assertEquals(2, whoa.dequeue().size());
                assertEquals(0, whoa.dequeue().size());
                assertEquals(1, whoa.size());
        }
        
        public void test10() {
                for (int power = 0; power < 20; power++) {
                        queue = new Queue<>();
                        int i;
                        System.out.println("Power " + power);
                        for (i = 0; i < 1 << power; i++)
                                queue.enqueue(i);
                        testQueue(queue, IntStream.rangeClosed(0,i-1).toArray());
                }
        }

        public void test11() {
                queue.enqueue(0);
                testQueue(queue, 0);
                queue = new Queue<>();
                queue.enqueue(0);
                queue.enqueue(1);
                queue.dequeue();
                queue.dequeue();
                queue.enqueue(2);
                queue.enqueue(3);
                queue.enqueue(4);
                queue.enqueue(5);
                queue.enqueue(6);
                testQueue(queue, 2, 3, 4, 5, 6);
        }
        
        public void test12() {
                try {
                        queue.enqueue(null);
                        assertFalse("adding a null element should throw an IllegalArgumentException",true);
                }
                catch (RuntimeException ex) {
                        assertTrue("wrong exception... " + ex, ex instanceof IllegalArgumentException);
                }
        }


        protected void testQueue(Queue<Integer> queue, int... elements)
        {
                assertEquals("size() is off... ",elements.length,queue.size());
                Integer current = null;
                int i = 0;
                while (!queue.isEmpty()) {
                        try {
                                assertEquals(elements[i], (int) queue.front());
                                current = queue.dequeue();
                                assertEquals(elements[i], (int) current);
                        }
                        catch (NoSuchElementException e) {
                                assertTrue("expected "+elements[i]+" but received NoSuchElement exception...",false);
                        }
                        catch (IndexOutOfBoundsException e) {
                                assertTrue("expected empty queue but received "+current,false);
                        }
                        catch (Exception e) {
                                assertTrue("exception should not have been thrown... "+e.getMessage(),false);
                        }
                        ++i;
                }
                assertTrue(queue.isEmpty());
                try {
                        queue.front();
                        assertFalse("front on empty queue should throw exception... ",true);
                } catch (RuntimeException ex) {
                        assertTrue("wrong exception... " + ex, ex instanceof NoSuchElementException);
                }
                try {
                        queue.dequeue();
                        assertFalse("dequeue on empty queue should throw exception",true);
                } catch (RuntimeException ex) {
                        assertTrue("wrong exception... " + ex, ex instanceof NoSuchElementException);
                }
        }
        
        public void test20() {
        	int power = 30;
        	queue.enqueue(0);
        	for (int p = 0; p < power; ++p) {
        		for (int i=1<<p; i < 1<<(p+1); ++i) {
        			queue.enqueue(i);
        			assertEquals(Integer.valueOf(i-1), queue.dequeue());
        			assertEquals(1, queue.size());
        		}
        	}
        }
}
