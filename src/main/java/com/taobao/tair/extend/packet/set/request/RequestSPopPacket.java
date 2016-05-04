package com.taobao.tair.extend.packet.set.request;

import com.taobao.tair.comm.Transcoder;
import com.taobao.tair.etc.TairConstant;
import com.taobao.tair.packet.BasePacket;

public class RequestSPopPacket extends BasePacket {

	private static final int HEADER_LEN = 1 + 2 + 2 + 4 + 4;
	
	private short namespace = 0;
	private short version = 0;
	private int expire = 0;
	private Object key = null;
	
	public RequestSPopPacket(Transcoder transcoder) {
		super(transcoder);
		pcode = TairConstant.TAIR_REQ_SPOP_PACKET;
	}

	public RequestSPopPacket() {
		super();
		pcode = TairConstant.TAIR_REQ_SPOP_PACKET;
	}

	public int encode() {
		byte[] keybytes = null;
		if (key == null) {
			return TairConstant.SERIALIZEERROR;
		}
		try {
			keybytes = transcoder.encode(key);
		} catch (Throwable e) {
			return TairConstant.SERIALIZEERROR;
		}
		if(keybytes == null) {
			return TairConstant.SERIALIZEERROR;
		}
		
		if(keybytes.length >= TairConstant.TAIR_KEY_MAX_LENTH) {
			return TairConstant.KEYTOLARGE;
		}
		
		writePacketBegin(HEADER_LEN + keybytes.length);
		byteBuffer.put((byte)0);
		byteBuffer.putShort(namespace);
		byteBuffer.putShort(version);
		byteBuffer.putInt(expire);
		byteBuffer.putInt(keybytes.length);
		byteBuffer.put(keybytes);
		writePacketEnd();
		
		return 0;
	}
	
	public boolean decode() {
		throw new UnsupportedOperationException();
	}
	
	public void setNamespace(short namespace) {
		this.namespace = namespace;
	}
	public short getNamespace() {
		return this.namespace;
	}
	
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getKey() {
		return this.key;
	}
	
	public void setVersion(short version) {
		this.version = version;
	}
	public short getVersion() {
		return this.version;
	}
	
	public void setExpire(int expire) {
		this.expire = expire;
	}
	public int getExpire() {
		return this.expire;
	}
}