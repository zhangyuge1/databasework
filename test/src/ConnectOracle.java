import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ConnectOracle {
    private Connection connection;
    private Statement statement;
    //连接数据库
    public ConnectOracle()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb", "zhangyuge", "Zcq9891zl");
            statement=connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //数据库初始化，建表、视图、存储过程,插入数据
    public boolean init()
    {
        if(createProcedure())
            return true;
        else
            return false;
    }
    //数据库建表
    public boolean createTable()
    {
        try {
            String table_supermanager="create table supermanager\n" +
                    "(管理员id char(10) not null,\n" +
                    " 用户名   varchar(16) not null,\n" +
                    " 密码    varchar2(20) not null,\n" +
                    " constraint pk_supermanager primary key(管理员id)\n" +
                    ")";
            String table_shelter="create table shelter\n" +
                    "(收容所id char(10) not null,\n" +
                    "收容所名称 varchar2(20) not null,\n" +
                    "地址  varchar2(100) not null,\n" +
                    "邮编  char(6)  not null,\n" +
                    "总房间数 integer not null,\n" +
                    "剩余房间数 integer not null,\n" +
                    "备注 varchar2(400),\n" +
                    "constraint pk_shelter primary key(收容所id)\n" +
                    ")";
            String table_shelteruser="create table shelteruser\n" +
                    "(用户id char(10) not null,\n" +
                    "用户名 varchar(16) not null,\n" +
                    "密码 varchar2(20) not null,\n" +
                    "邮箱 varchar2(20),\n" +
                    "手机号 char(11),\n" +
                    "收容所id char(10) not null,\n" +
                    "constraint pk_user primary key(用户id),\n" +
                    "constraint fk_user foreign key(收容所id) references shelter(收容所id) \n" +
                    ")";
            String table_animalsinf="create table animalsinf\n" +
                    "(动物id char(10) not null,\n" +
                    "动物编号 varchar2(10) not null,\n" +
                    "动物名 varchar2(20) not null,\n" +
                    "种类 varchar2(20) not null,\n" +
                    "性别 char(1) not null,\n" +
                    "年龄 integer not null,\n" +
                    "图像 varchar2(100),\n" +
                    "收容所id char(10) not null,\n" +
                    "constraint pk_animalsinf primary key(动物id),\n" +
                    "constraint fk_animalsinf foreign key(收容所id) references shelter(收容所id) \n" +
                    ")";
            String table_healthinf="create table healthinf\n" +
                    "(健康信息id char(10) not null,\n" +
                    "动物id char(10) not null,\n" +
                    "用户id char(10) not null,\n" +
                    "健康信息 varchar2(400) not null,\n" +
                    "检查日期 date not null,\n" +
                    "备注 varchar2(400),\n" +
                    "constraint pk_healthinf primary key(健康信息id),\n" +
                    "constraint fk_hanimal foreign key(动物id) references animalsinf(动物id),\n" +
                    "constraint fk_huser foreign key(用户id) references shelteruser(用户id)\n" +
                    ")";
            String table_vaccineinf="create table vaccineinf\n" +
                    "(疫苗信息id char(10) not null,\n" +
                    "动物id char(10) not null,\n" +
                    "用户id char(10) not null,\n" +
                    "疫苗名 varchar2(20) not null,\n" +
                    "接种日期 date not null,\n" +
                    "备注 varchar2(400),\n" +
                    "constraint pk_vaccineinf primary key(疫苗信息id),\n" +
                    "constraint fk_vanimal foreign key(动物id) references animalsinf(动物id),\n" +
                    "constraint fk_vuser foreign key(用户id) references shelteruser(用户id)\n" +
                    ")";
            statement.executeUpdate(table_supermanager);
            statement.executeUpdate(table_shelter);
            statement.executeUpdate(table_shelteruser);
            statement.executeUpdate(table_animalsinf);
            statement.executeUpdate(table_healthinf);
            statement.executeUpdate(table_vaccineinf);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //数据库插入数据
    public boolean insertdata()
    {
        try {
            //往supermanager插入数据
            statement.executeUpdate("insert into supermanager values('2019100100','s1','123456')");
            statement.executeUpdate("insert into supermanager values('2019100101','s2','123456')");
            //向shelter 插入数据
            statement.executeUpdate("insert into shelter values('4001100123','第一收容所','湖北武汉洪山区','430074','100','100','')");
            statement.executeUpdate("insert into shelter values('4001100124','第二收容所','湖北武汉洪山区','430074','100','100','')");
            //向shelteruser插入数据
            statement.executeUpdate("insert into shelteruser(用户id,用户名,密码,收容所id) values('2019200100','u1','000000','4001100123')");
            statement.executeUpdate("insert into shelteruser(用户id,用户名,密码,收容所id) values('2019200101','u2','000000','4001100123')");
            statement.executeUpdate("insert into shelteruser(用户id,用户名,密码,收容所id) values('2019200102','u3','000000','4001100123')");
            statement.executeUpdate("insert into shelteruser(用户id,用户名,密码,收容所id) values('2019200103','u4','000000','4001100124')");
            statement.executeUpdate("insert into shelteruser(用户id,用户名,密码,收容所id) values('2019200104','u5','000000','4001100124')");
            statement.executeUpdate("insert into shelteruser(用户id,用户名,密码,收容所id) values('2019200105','u6','000000','4001100124')");
            //向animalsinf插入数据
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500100','A1','a1','猫','1',1,'4001100123')");
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500101','B1','a2','狗','0',2,'4001100123')");
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500102','C1','a1','虎','0',1,'4001100124')");
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500103','D1','a1','兔','1',2,'4001100124')");
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500104','A1','a1','猫','0',1,'4001100123')");
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500105','B1','a1','狗','1',2,'4001100124')");
            statement.executeUpdate("insert into animalsinf(动物id,动物编号,动物名,种类,性别,年龄,收容所id) values('2017500106','C1','a1','虎','1',1,'4001100123')");
            //向healthinf插入数据
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200100','2017500100','2019200100','良好',to_date('2019-10-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200101','2017500101','2019200101','良好',to_date('2018-09-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200102','2017500102','2019200102','良好',to_date('2019-09-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200103','2017500103','2019200103','良好',to_date('2018-08-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200104','2017500104','2019200104','良好',to_date('2019-07-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200105','2017500105','2019200105','良好',to_date('2018-10-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into healthinf(健康信息id,动物id,用户id,健康信息,检查日期) values('1001200106','2017500106','2019200100','良好',to_date('2019-10-14','yyyy-mm-dd'))");
            //向vaccineinf插入数据
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200100','2017500100','2019200100','b1',to_date('2019-10-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200101','2017500101','2019200101','b1',to_date('2018-09-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200102','2017500102','2019200102','b1',to_date('2019-09-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200103','2017500103','2019200103','b1',to_date('2018-08-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200104','2017500104','2019200104','b1',to_date('2019-07-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200105','2017500105','2019200105','b1',to_date('2018-10-14','yyyy-mm-dd'))");
            statement.executeUpdate("insert into vaccineinf(疫苗信息id,动物id,用户id,疫苗名,接种日期) values('1001200106','2017500106','2019200100','b1',to_date('2019-10-14','yyyy-mm-dd'))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    //数据库建立存储过程
    public boolean createProcedure()
    {
        //创建存储过程最后一定要加分号
        String procedure_getuser="create or replace procedure zhangyuge.getuser(userid in varchar2,userpassword in varchar2,isuser in varchar2,username out varchar2)\n" +
                "as\n" +
                "ans integer:=0;\n" +
                "begin\n" +
                "if isuser = '1' then\n" +
                "select count(*) into ans\n" +
                "from zhangyuge.shelteruser\n" +
                "where 用户id=userid and 密码=userpassword;\n" +
                "if ans=0 then\n" +
                "username:='';\n" +
                "else\n" +
                "select 用户名 into username\n" +
                "from zhangyuge.shelteruser\n" +
                "where 用户id=userid and 密码=userpassword;\n" +
                "end if;\n" +
                "else\n" +
                "select count(*) into ans\n" +
                "from zhangyuge.supermanager\n" +
                "where 管理员id=userid and 密码=userpassword;\n" +
                "if ans=0 then\n" +
                "username:='';\n" +
                "else\n" +
                "select 用户名 into username\n" +
                "from zhangyuge.supermanager\n" +
                "where 管理员id=userid and 密码=userpassword;\n" +
                "end if;\n" +
                "end if; \n" +
                "end;";
        //输入收容所id，无返回值，但修改该收容所的剩余房间数为（剩余房间数-1）
        String procedure_updaterestroom="create or replace procedure updaterestroom(shelterid in varchar2)\n" +
                "as\n" +
                "restroom integer;\n" +
                "begin\n" +
                "select 剩余房间数 into restroom from shelter where 收容所id=shelterid;\n" +
                "update shelter set 剩余房间数=restroom+1 where 收容所id=shelterid;\n" +
                "end;";
        //输入动物id，为该动物的年龄加1
        String procedure_updateanimalage="create or replace procedure updateanimalage(animalid in varchar2)\n" +
                "as\n" +
                "age integer;\n" +
                "begin\n" +
                "select 年龄 into age from animalsinf where 动物id=animalid;\n" +
                "update animalsinf set 年龄=age+1 where 动物id=animalid;\n" +
                "end;";
        //返回视图animal_age(动物id,年龄,检查日期)中所有动物最早一次体检的记录,cur为存放结果的游标
        String procedure_getdate="create or replace procedure getdate(cur out sys_refcursor)\n" +
                "as\n" +
                "begin\n" +
                "open cur for\n" +
                "select 动物id,年龄,min(检查日期) 第一次检查日期 from animal_age\n" +
                "group by 动物id,年龄;\n" +
                "end;";
        try {
            statement.executeUpdate(procedure_getuser);
            statement.executeUpdate(procedure_updateanimalage);
            statement.executeUpdate(procedure_updaterestroom);
            statement.executeUpdate(procedure_getdate);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //数据库建立视图
    public boolean createView()
    {
        String view_animal_age="create view animal_age(动物id,年龄,第一次检查日期)\n" +
                "as\n" +
                "select 动物id,年龄,检查日期\n" +
                "from animalsinf,healthinf\n" +
                "where animalsinf.动物id=healthinf.动物id";
        try {
            statement.executeUpdate(view_animal_age);
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    //查询用户信息是否在表中存在
    String queryUser(String userid,String password,String isuser)
    {
        String res="";
        try {
            CallableStatement callableStatement=connection.prepareCall("{call getuser(?,?,?,?)}");
            callableStatement.setString(1,userid);
            callableStatement.setString(2,password);
            callableStatement.setString(3,isuser);
            callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
            callableStatement.execute();
            res = callableStatement.getString(4);
            if(res==null)
                res="";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    //自动维护剩余房间数，调用存储过程updaterestroom，当插入一条动物信息时调用
    boolean updaterestrom(String shelterid)
    {
        CallableStatement callableStatement= null;
        try {
            callableStatement = connection.prepareCall("{call updaterestroom(?)}");
            callableStatement.setString(1,shelterid);
            callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //自动维护动物年龄，调用存储过程获得所有动物第一次体检的时间，然后和当前时间进行比较看是否需要更新
    boolean updateanimalage()
    {
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currenttime=dateFormat.format(date);
        String []curtime=currenttime.split("-");
        CallableStatement callableStatement= null;
        try {
            callableStatement = connection.prepareCall("{call getdate(?)}");
            callableStatement.registerOutParameter(1,OracleTypes.CURSOR);
            callableStatement.execute();
            ResultSet resultSet=((OracleCallableStatement)callableStatement).getCursor(1);
            while(resultSet.next())
            {
                String id=resultSet.getString(1);
                int age=resultSet.getInt(2);
                String animaldate=String.valueOf(resultSet.getDate(3));
                String animaldates[]=animaldate.split("-");
                //如果当前时间（月日）与该动物第一次体检的时间相等，则动物年龄加1
                if(curtime[1]==animaldates[1]&&curtime[2]==animaldates[2])
                {
                    callableStatement=connection.prepareCall("{call updateanimalage(?)}");
                    callableStatement.setString(1,id);
                    callableStatement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //执行查询语句，并将结果放在一个二维数组返回，参数which表示哪个表被查询，参数表示查询的限制，相当于查询的where
    public ArrayList<String> query(int which,ArrayList<String> limit)
    {
        ArrayList<String> res=new ArrayList<>();
        //which=1,动物信息查询
        if(which==1)
        {
            String sql="";
            if(limit.size()==0)
            {
                sql="select * from animalsinf";
            }
            else
            {
                sql="select * from animalsinf where ";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                    {
                        sql+=limit.get(i);
                    }
                    else
                    {
                        sql+=" and "+limit.get(i);
                    }
                }
            }
            try {
                System.out.println(sql);
                ResultSet resultSet=statement.executeQuery(sql);
                while(resultSet.next())
                {
                    StringBuilder builder=new StringBuilder();
                    builder.append(resultSet.getString("动物id"));
                    builder.append(" "+resultSet.getString("动物名"));
                    builder.append(" "+resultSet.getString("动物编号"));
                    builder.append(" "+resultSet.getString("种类"));
                    builder.append(" "+resultSet.getString("性别"));
                    builder.append(" "+String.valueOf(resultSet.getInt("年龄")));
                    if(resultSet.getString("图像")==null)
                        builder.append(" null");
                    else
                        builder.append(" "+resultSet.getString("图像"));
                    builder.append(" "+resultSet.getString("收容所id"));
                    res.add(builder.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //which=2，收容所信息查询
        else if(which==2)
        {
            String sql="";
            if(limit.size()==0)
            {
                sql="select * from shelter";
            }
            else
            {
                sql="select * from shelter where ";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                    {
                        sql+=limit.get(i);
                    }
                    else
                    {
                        sql+=" and "+limit.get(i);
                    }
                }
            }
            try {
                System.out.println(sql);
                ResultSet resultSet=statement.executeQuery(sql);
                while(resultSet.next())
                {
                    StringBuilder builder=new StringBuilder();
                    builder.append(resultSet.getString("收容所id"));
                    builder.append(" "+resultSet.getString("收容所名称"));
                    builder.append(" "+resultSet.getString("地址"));
                    builder.append(" "+resultSet.getString("邮编"));
                    builder.append(" "+String.valueOf(resultSet.getInt("总房间数")));
                    builder.append(" "+String.valueOf(resultSet.getInt("剩余房间数")));
                    if(resultSet.getString("备注")==null)
                        builder.append(" null");
                    else
                        builder.append(" "+resultSet.getString("备注"));
                    res.add(builder.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //which=3，健康信息查询
        else if(which==3)
        {
            String sql="";
            if(limit.size()==0)
            {
                sql="select * from healthinf";
            }
            else
            {
                sql="select * from healthinf where ";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                    {
                        sql+=limit.get(i);
                    }
                    else
                    {
                        sql+=" and "+limit.get(i);
                    }
                }
            }
            try {
                System.out.println(sql);
                ResultSet resultSet=statement.executeQuery(sql);
                while(resultSet.next())
                {
                    StringBuilder builder=new StringBuilder();
                    builder.append(resultSet.getString("健康信息id"));
                    builder.append(" "+resultSet.getString("动物id"));
                    builder.append(" "+resultSet.getString("用户id"));
                    builder.append(" "+resultSet.getString("健康信息"));
                    builder.append(" "+String.valueOf(resultSet.getDate("检查日期")));
                    if(resultSet.getString("备注")==null)
                        builder.append(" null");
                    else
                        builder.append(" "+resultSet.getString("备注"));
                    res.add(builder.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //which=4，疫苗信息查询
        else
        {
            String sql="";
            if(limit.size()==0)
            {
                sql="select * from vaccineinf";
            }
            else
            {
                sql="select * from vaccineinf where ";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                    {
                        sql+=limit.get(i);
                    }
                    else
                    {
                        sql+=" and "+limit.get(i);
                    }
                }
            }
            try {
                System.out.println(sql);
                ResultSet resultSet=statement.executeQuery(sql);
                while(resultSet.next())
                {
                    StringBuilder builder=new StringBuilder();
                    builder.append(resultSet.getString("疫苗信息id"));
                    builder.append(" "+resultSet.getString("动物id"));
                    builder.append(" "+resultSet.getString("用户id"));
                    builder.append(" "+resultSet.getString("疫苗名"));
                    builder.append(" "+String.valueOf(resultSet.getDate("接种日期")));
                    if(resultSet.getString("备注")==null)
                        builder.append(" null");
                    else
                        builder.append(" "+resultSet.getString("备注"));
                    res.add(builder.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    //执行插入语句,返回是否插入成功，插入成功则返回true，参数which表示哪个表被查询，data是要插入的数据
    public boolean insert(int which,ArrayList<String> data)
    {
        //which=1,动物信息插入
        if(which==1)
        {
            String sql="insert into animalsinf values(";
            for(int i=0;i<data.size();i++)
            {
                if(i==0)
                    sql=sql+"'"+data.get(i)+"'";
                else
                    sql=sql+",'"+data.get(i)+"'";
            }
            sql=sql+")";
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            if(!updaterestrom(data.get(data.size()-1)))
                return false;
        }
        //which=2，收容所信息插入
        else if(which==2)
        {
            String sql="insert into shelter values(";
            for(int i=0;i<data.size();i++)
            {
                if(i==0)
                    sql=sql+"'"+data.get(i)+"'";
                else
                    sql=sql+",'"+data.get(i)+"'";
            }
            sql=sql+")";
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        //which=3，健康信息插入
        else if(which==3)
        {
            String sql="insert into healthinf values(";
            for(int i=0;i<data.size();i++)
            {
                if(i==0)
                    sql=sql+"'"+data.get(i)+"'";
                else {
                    if(i==4)
                    {
                        sql=sql+",to_date('"+data.get(i)+"','yyyy-mm-dd')";
                    }
                    else
                        sql=sql+",'"+data.get(i)+"'";
                }
            }
            sql=sql+")";
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        //which=4，疫苗信息插入
        else
        {
            String sql="insert into vaccineinf values(";
            for(int i=0;i<data.size();i++)
            {
                if(i==0)
                    sql=sql+"'"+data.get(i)+"'";
                else {
                    if(i==4)
                    {
                        sql=sql+",to_date('"+data.get(i)+"','yyyy-mm-dd')";
                    }
                    else
                        sql=sql+",'"+data.get(i)+"'";
                }
            }
            sql=sql+")";
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    //执行删除语句，返回是否删除成功，删除成功返回true，参数which表示哪个表被查询，limit表示要删除的行的条件
    public boolean delete(int which,ArrayList<String> limit)
    {
        //which=1,动物信息删除
        if(which==1)
        {
            if(limit.size()>0)
            {
                String sql="delete from animalsinf where";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                        sql+=" "+limit.get(i);
                    else
                        sql+=" and "+limit.get(i);
                }
                try {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        //which=2，收容所信息删除
        else if(which==2)
        {
            if(limit.size()>0)
            {
                String sql="delete from shelter where";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                        sql+=" "+limit.get(i);
                    else
                        sql+=" and "+limit.get(i);
                }
                try {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        //which=3，健康信息删除
        else if(which==3)
        {
            if(limit.size()>0)
            {
                String sql="delete from healthinf where";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                        sql+=" "+limit.get(i);
                    else
                        sql+=" and "+limit.get(i);
                }
                try {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        //which=4，疫苗信息删除
        else
        {
            if(limit.size()>0)
            {
                String sql="delete from vaccineinf where";
                for(int i=0;i<limit.size();i++)
                {
                    if(i==0)
                        sql+=" "+limit.get(i);
                    else
                        sql+=" and "+limit.get(i);
                }
                try {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    //执行update语句,返回是否插入成功，插入成功则返回true，参数which表示哪个表被查询，id表示要修改的动物id，data是要修改的数据
    public boolean update(int which, String id,HashMap<String,String> data)
    {
        //which=1,动物信息更新
        if(which==1)
        {
            String sql="update animalsinf set ";
            Iterator iter = data.entrySet().iterator();
            boolean flag=true;
            while(iter.hasNext())
            {
                Map.Entry entry = (Map.Entry)iter.next();
                String key=(String)entry.getKey();
                String value=(String)entry.getValue();
                if(flag)
                {
                    flag=false;
                    sql=sql+key+"='"+value+"'";
                }
                else
                    sql=sql+","+key+"='"+value+"'";
            }
            sql+=" where 动物id="+id;
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        //which=2，收容所信息更新
        else if(which==2)
        {
            String sql="update shelter set ";
            Iterator iter = data.entrySet().iterator();
            boolean flag=true;
            while(iter.hasNext())
            {
                Map.Entry entry = (Map.Entry)iter.next();
                String key=(String)entry.getKey();
                String value=(String)entry.getValue();
                if(flag)
                {
                    flag=false;
                    sql=sql+key+"='"+value+"'";
                }
                else
                    sql=sql+","+key+"='"+value+"'";
            }
            sql+=" where 收容所id="+id;
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        //which=3，健康信息更新
        else if(which==3)
        {
            String sql="update healthinf set ";
            Iterator iter = data.entrySet().iterator();
            boolean flag=true;
            while(iter.hasNext())
            {
                Map.Entry entry = (Map.Entry)iter.next();
                String key=(String)entry.getKey();
                String value=(String)entry.getValue();
                if(flag)
                {
                    flag=false;
                    sql=sql+key+"='"+value+"'";
                }
                else {
                    if(key.equals("检查日期"))
                    {
                        sql=sql+","+key+"="+"to_date('"+value+"','yyyy-mm-dd')";
                    }
                    else
                        sql=sql+","+key+"='"+value+"'";
                }
            }
            sql+=" where 健康信息id="+id;
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        //which=4，疫苗信息更新
        else
        {
            String sql="update vaccineinf set ";
            Iterator iter = data.entrySet().iterator();
            boolean flag=true;
            while(iter.hasNext())
            {
                Map.Entry entry = (Map.Entry)iter.next();
                String key=(String)entry.getKey();
                String value=(String)entry.getValue();
                if(flag)
                {
                    flag=false;
                    sql=sql+key+"='"+value+"'";
                }
                else {
                    if(key.equals("接种日期"))
                    {
                        sql=sql+","+key+"="+"to_date('"+value+"','yyyy-mm-dd')";
                    }
                    else
                        sql=sql+","+key+"='"+value+"'";
                }
            }
            sql+=" where 疫苗信息id="+id;
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
