package base.conn;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Teste {
	
	
	public static void main(String[] args) {
		CircularFifoQueue<String> queue = new CircularFifoQueue<>();
//		queue.add("a");
//		queue.add("b");
//		queue.add("c");
//		queue.add("d");
//		queue.add("e");
//		queue.add("f");
//		System.out.println("max size: "+queue.maxSize());
		System.out.println(queue);
		
//		System.out.println("get element: "+queue.get(1));
		System.out.println("poll: "+queue.poll());
//		queue.poll();
//		System.out.println(queue); //outputs [c, d]
	}

}
