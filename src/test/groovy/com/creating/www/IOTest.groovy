package com.creating.www

import static org.junit.jupiter.api.Assertions.*

import com.creating.www.beans.Person
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.junit.Ignore
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import groovy.io.FileType
import groovy.io.FileVisitResult

class IOTest {
	static Logger logger=LogManager.getLogger()
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	/**
	 * 文件输入流测试 
	 * */
	@Ignore
	@Test
	void testInputStream() {
		//logger.debug("hi,groovy")
		def baseDir='doc'
		new File(baseDir, 'alarmRuleResult.csv').eachLine { line -> println line }
		new File(baseDir,'alarmRuleResult.csv').eachLine {line,nb-> println "line:$nb,$line"}
		def line=null
		new File(baseDir,'alarmRuleResult.csv').withReader { reader->
			while(line=reader.readLine()) {
				println "$line"
			}
		}
		def lines=new File(baseDir,'alarmRuleResult.csv').collect{it}
		for(l in lines) {
			println "$l"
		}
		lines=new File(baseDir,'alarmRuleResult.csv') as String[]
		for(l in lines) {
			println "$l"
		}
		def contents=new File(baseDir,'alarmRuleResult.csv').bytes
		println "file-length:$contents.length"
		def is=new File(baseDir,'alarmRuleResult.csv').newInputStream()
		//do something
		is.close()
		new File(baseDir,'alarmRuleResult.csv').withInputStream { stream->
			//do something
		}

	}
	/**
	 * 文件输出测试
	 * */
	@Ignore
	@Test
	void testOutPutStream() {
		def baseDir="temp"
		new File(baseDir,".cache").withWriter("UTF-8") { writer->
			writer.writeLine('A')
			writer.writeLine('B')
			writer.writeLine('C')
		}
		new File(baseDir,".cache") << '''A
B
C'''
		new File(baseDir,".cache").bytes=['A', '\n', 'B', '\n', 'C', '\n', 'D']
		def op=new File(baseDir,".cache").newOutputStream()
		//do something
		op.close()
		new File(baseDir,".cache").withOutputStream { stream->
			//do something
		}
	}
	/**
	 * 1.3 Traversing file trees
	 * */
	@Test
	void testTranversingFileTrees() {
		def dir=new File(".")
		dir.eachFile { file-> println "$file.name"  }
		println "--------------------"
		dir.eachFileMatch(~/.*\.xml/) { file-> println "$file.name" }//用点转义*//单层
		dir.eachDirMatch(~/.test/) { file-> println "$file.name" }//单层
		dir.eachFileRecurse { file-> println "$file.name"  }//递归
		dir.eachFileRecurse(FileType.FILES) { file-> println "$file.name"  }//递归，只文件
		dir.traverse { file->
			if(file.name.equals("classes")) {
				FileVisitResult.TERMINATE
			}else {
				println "$file.name"
				FileVisitResult.CONTINUE
			}
		}
	}
	/**
	 * 1.4 Data and objects
	 * */
	@Test
	void testDataandObjects() {
		def file=new File("temp","datas.txt")
		def message="hi,groovy"
		file.withDataOutputStream { outd->
			outd.writeBoolean(true)
			outd.writeUTF(message)
		}
		file.withDataInputStream { ind->
			assert ind.readBoolean()==true
			assert ind.readUTF()==message
		}
		file=new File("temp","object.txt")
		def person=new Person()
		file.withObjectOutputStream { outd->
			outd.writeObject(person)
		}
		file.withObjectInputStream { ind->
			assert ind.readObject() == person
		}
	}
	/**
	 * 1.5 Executing External Processes
	 * */
	@Test
	void testExecute() {
		def process="ls -l".execute()
		println "${process.text}"
		process="ls -l".execute()
		process.in.eachLine { line->
			println "$line"
		}
		process = "dir".execute()
		println "${process.text}"
		process = "CMD /c dir".execute()
		println "${process.text}"
		
		//高级用法，暂时没弄
	}
}
