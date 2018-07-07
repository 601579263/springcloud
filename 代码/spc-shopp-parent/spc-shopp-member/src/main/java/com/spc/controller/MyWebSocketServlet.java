package com.spc.controller;
/**
 * 微信小程序WebSocket接口
 * @author 60157
 *
 */

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//ws:localhost:8762/websocket/1
/**
 * 微信小程序 websocket调用接口
 * @author 60157
 *
 */
@ServerEndpoint(value="/websocket")
@Component
public class MyWebSocketServlet{

	private Session session;
	
	@OnOpen
	public void open(Session session) {
		this.session=session;
		System.out.println("有连接进来了........sessionId:"+session.getId());
	}
	
	@OnMessage
	public void inMessage(String message) {
		System.out.println("======收到消息了,sessionid是:"+this.session.getId());
		System.out.println("==========收到消息内容是:"+message);
		try {
			session.getBasicRemote().sendText("你好我已经收到你的内容,你发送的内容是:"+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void end() {
		System.out.println("有连接关闭了,sessionid是:"+this.session.getId());
	}
	/**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
	
	
}
