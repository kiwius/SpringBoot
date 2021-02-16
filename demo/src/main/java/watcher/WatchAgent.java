package test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class WatchAgent {
	private static final String filePath = System.getProperty("usr.dir");
	private WatchKey watchKey;

	public void init() throws IOException {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(filePath);

		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);

		Thread thread = new Thread(() -> {
			while (true) {
				try {
					watchKey = watchService.take();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for(WatchEvent<?> event : events) {
					Kind<?> kind = event.kind();
					Path paths = (Path)event.context();
					System.out.println("absolutePath : " + paths.toAbsolutePath());
					if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE))
						System.out.println("CREATE");
					else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE))
						System.out.println("DELETE");
					else if(kind.equals(StandardWatchEventKinds.ENTRY_MODIFY))
						System.out.println("MODIFY");
					else if(kind.equals(StandardWatchEventKinds.OVERFLOW))
						System.out.println("OVERFLOW");
					else
						System.out.println("HELLO");
				}
				
				if(!watchKey.reset()) {
					try {
						watchService.close();
					} catch (Exception e2) {
						e2.printStackTrace();
						// TODO: handle exception
					}
				}
			}
		});
		thread.start();
	}
}
