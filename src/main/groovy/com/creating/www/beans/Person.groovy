package com.creating.www.beans

class Person implements Serializable {
public int x=1
public String y=3
@Override
	public boolean equals(Object obj) {
		if(obj==null) return false
		if(!(obj instanceof Person)) return false
		Person trans=(Person)obj;
		if(x==trans.x&&y.equals(trans.y))
		true
		else
		false
	}
}
