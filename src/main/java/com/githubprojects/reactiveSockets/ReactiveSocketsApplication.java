package com.githubprojects.reactiveSockets;

import com.githubprojects.reactiveSockets.sync.SyncClient;
import com.githubprojects.reactiveSockets.sync.SyncServer;


public class ReactiveSocketsApplication {
	public static void main(String[] args) {
		int port = 8080;
		SyncServer server = new SyncServer();
		new Thread(() -> server.start(port)).start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		SyncClient client = new SyncClient();
		client.startClient(port);
	}
}
