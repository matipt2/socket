package com.githubprojects.reactiveSockets;

import com.githubprojects.reactiveSockets.async.AsyncClient;
import com.githubprojects.reactiveSockets.async.AsyncServer;

public class ReactiveSocketsApplication {
	public static void main(String[] args) throws InterruptedException {
//		int port = 8080;
//		SyncServer server = new SyncServer();
//		new Thread(() -> server.start(port)).start();
//
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		}
//
//		SyncClient client = new SyncClient();
//		client.startClient(port);

		int port = 6065;
		AsyncServer asyncServer = new AsyncServer();
		AsyncClient asyncClient = new AsyncClient();

		new Thread(() -> asyncServer.start(port)).start();
		Thread.sleep(2000);
		asyncClient.start(port);
	}
}
