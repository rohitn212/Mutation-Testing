import java.util.*;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class ProcessUnits
{
    public static HashMap<String, Integer> hm = new HashMap<String, Integer>();
    //Mapper class
    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable ,/*Input key Type */
                    Text,                /*Input value Type*/
                    Text,                /*Output key Type*/
                    Text>        /*Output value Type*/
    {

        //Map function
        public void map(LongWritable key, Text value,
                        OutputCollector<Text, Text> output,
                        Reporter reporter) throws IOException {

            String line = value.toString();
            StringTokenizer s = new StringTokenizer(line," ");

            String lineNumber = s.nextToken();
            String testName = s.nextToken();
            String className = s.nextToken();

            String outKey = lineNumber + ":" + className;
            String outValue = testName;
            hm.put(testName, hm.getOrDefault(testName, 0) + 1);

            output.collect(new Text(outKey), new Text(outValue));
        }
    }


    //Reducer class
    public static class E_EReduce extends MapReduceBase implements
            Reducer< Text, Text, Text, Text>
    {

        //Reduce function
        public void reduce( Text key, Iterator <Text> values,
                            OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append("<");
            ArrayList<String> al = new ArrayList<String>();
            while(values.hasNext()) {
                String val = values.next().toString();
                hm.get(val);
                al.add(val);
            }
            al = sortValues(al);
            for(int i = 0; i < al.size(); i++) {
                sb.append(al.get(i) + ", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append(">");
            output.collect(key, new Text(sb.toString()));

        }
    }

    public static ArrayList<String> sortValues(ArrayList<String> list) {
        int length = list.size();
        for(int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for(int j = i + 1; j < length; j++) {
                if (list.get(j).compareTo(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            String temp = list.get(minIndex);
            list.set(minIndex, list.get(i));
            list.set(i, temp);
        }
        return list;
    }


    //Main function
    public static void main(String args[])throws Exception
    {
        JobConf conf = new JobConf(ProcessUnits.class);

        conf.setJobName("overlappingCoverage");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapperClass(E_EMapper.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}