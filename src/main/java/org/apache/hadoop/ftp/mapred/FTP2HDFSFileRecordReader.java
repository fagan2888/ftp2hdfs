package org.apache.hadoop.ftp.mapred;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class FTP2HDFSFileRecordReader extends RecordReader<Text, NullWritable> {

	private boolean processed = false;

	private Text key = new Text();
	private String remoteFile = null;

	/*
	 * public FTP2HDFSFileRecordReader(FileSplit fileSplit, Configuration conf)
	 * throws IOException { this.fileSplit = fileSplit; this.conf = conf; }
	 */
	
    public FTP2HDFSFileRecordReader(InputSplit split, TaskAttemptContext context) {
        try {
			initialize(split, context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FTP2HDFSInputSplit ftpSplit = (FTP2HDFSInputSplit) split;
		remoteFile = ftpSplit.getCurrentDataset();
    }

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (!processed) {
				key= new Text(remoteFile);
				processed = true;
				return true;
		}
		return false;
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		return key;
	}

	@Override
	public NullWritable getCurrentValue() throws IOException, InterruptedException {
		return NullWritable.get();
	}

	@Override
	public float getProgress() throws IOException {
		return processed ? 1.0f : 0.0f;
	}

	@Override
	public void close() throws IOException {
		// do nothing
	}



}