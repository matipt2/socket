package com.githubprojects.reactiveSockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.githubprojects.reactiveSockets.http.AsyncHttpClient;
import com.githubprojects.reactiveSockets.http.AsyncHttpServer;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ReactiveSocketsApplication {
	public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
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
//
//		int port = 6065;
//		AsyncServer asyncServer = new AsyncServer();
//		AsyncClient asyncClient = new AsyncClient();
//
//		new Thread(() -> asyncServer.start(port)).start();
//		Thread.sleep(2000);
//		asyncClient.start(port);

		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		AsyncHttpServer asyncHttpServer = new AsyncHttpServer();
		new Thread(()-> asyncHttpServer.start(6606)).start();
		Thread.sleep(2000);
		asyncHttpClient.start(6606);
	}
}
