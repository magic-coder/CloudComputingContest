package imagecloud.hbase.table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor; 
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class TableAdmin {
	protected String tbname;
	protected String cfname;
	protected String clname;
	protected String rwname;
	//protected String hdfs_path;
	protected byte[] value;


	public TableAdmin() {
		super();
		this.tbname = null;
		this.cfname = null;
		this.clname = null;
		this.rwname = null;
		this.value  = null;
	}
	public TableAdmin(String tbname, String cfname, String clname,
			String phname, byte[] value) {
		super();
		this.tbname = tbname;
		this.cfname = cfname;
		this.clname = clname;
		this.rwname = phname;
		this.value  = new byte[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
		
	}
	public TableAdmin(String tbname, String cfname, String clname) {
		super();
		this.tbname = tbname;
		this.cfname = cfname;
		this.clname = clname;
		this.rwname = null;
		this.value  = null;
	}
	public void setTbname(String tbname) {
		this.tbname = tbname;
	}
	public void setCfname(String cfname) {
		this.cfname = cfname;
	}
	public void setClname(String clname) {
		this.clname = clname;
	}
	public void setRwname(String rwname) {
		this.rwname = rwname;
	}
	public void setValue(byte[] value) {
		this.value  = new byte[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}
	
	protected Configuration config(){
		Configuration config = HBaseConfiguration.create();
		config.addResource("./hbase-site.xml");
		config.addResource("./core-site.xml");
		config.set("hbase.zookeeper.quorum", "10.60.0.221");
		return config;
	}
	public void create() throws IOException {
		Configuration config = this.config();
		HBaseAdmin admin = null;
		admin = new HBaseAdmin(config);
		HTableDescriptor htd = new HTableDescriptor(tbname);
		HColumnDescriptor hcd = new HColumnDescriptor(cfname);
		htd.addFamily(hcd);
		admin.createTable(htd);
	}
	
	public void write() throws IOException{
		Configuration config = this.config();
		HTable table = null;
		table = new HTable(config, tbname);
		byte[] row = Bytes.toBytes(rwname); 
		Put p1 = new Put(row);
		/*add(byte[] family, byte[] qualifier, byte[] value)
		Add the specified column and value to this Put operation.*/
		p1.add(Bytes.toBytes(cfname), Bytes.toBytes(clname), value);
		table.put(p1);
	}
	
	public byte[] readByFamily(){
		return null;
	}
	
	public byte[] readByColumn() throws IOException{
		Configuration config = this.config();
		HTable table = null;
		//Scan scan = new Scan();
		byte[] requestedVal = null;
		table = new HTable(config, tbname);
		byte[] row = Bytes.toBytes(rwname);
		Get g = new Get(row);
		// g.addFamily(Bytes.toBytes(cfname));
		g.addColumn(cfname.getBytes(), clname.getBytes());
		/*
		 * add(byte[] family, byte[] qualifier, byte[] value) Add the specified
		 * column and value to this Put operation.
		 */
		Result result = table.get(g);
		requestedVal = result.getValue(cfname.getBytes(), clname.getBytes());

		return requestedVal;
	}
	

}
