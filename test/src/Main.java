import com.sun.deploy.panel.JreTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    ConnectOracle connectOracle;
    String username;
    boolean isuser;
    public Main()
    {
        connectOracle=new ConnectOracle();
    }
    //登录界面
    public void createLogin()
    {
        JFrame jFrame_login=new JFrame("动物收容所信息管理系统");
        jFrame_login.setLayout(null);
        jFrame_login.setBounds(400,400,450,400);
        jFrame_login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label_id=new JLabel("id：");
        JLabel label_password=new JLabel("密码：");
        JTextField jTextField_id=new JTextField();
        JPasswordField jPasswordField=new JPasswordField();
        label_id.setBounds(50,50,50,30);
        label_password.setBounds(50,120,50,30);
        jTextField_id.setBounds(120,50,200,30);
        jPasswordField.setBounds(120,120,200,30);
        jFrame_login.add(label_id);
        jFrame_login.add(label_password);
        jFrame_login.add(jPasswordField);
        jFrame_login.add(jTextField_id);

        JRadioButton jRadioButton_super=new JRadioButton("管理员");
        JRadioButton jRadioButton_user=new JRadioButton("用户");
        jRadioButton_super.setBounds(100,180,100,30);
        jRadioButton_user.setBounds(250,180,100,30);
        jFrame_login.add(jRadioButton_super);
        jFrame_login.add(jRadioButton_user);
        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(jRadioButton_super);
        buttonGroup.add(jRadioButton_user);
        jRadioButton_user.setSelected(true);
        JButton btn_login=new JButton("登录");
        btn_login.setBounds(100,250,60,30);
        jFrame_login.add(btn_login);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String password=new String(jPasswordField.getPassword());
                String res="";
                if(jRadioButton_super.isSelected())
                {
                    res=connectOracle.queryUser(id,password,"0");
                }
                else
                {
                    res=connectOracle.queryUser(id,password,"1");
                }
                if(res.equals(""))
                    JOptionPane.showMessageDialog(null, "该用户或管理员不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    username=res;
                    if(jRadioButton_super.isSelected())
                    {
                        isuser=false;
                        createSuperMain();
                        jFrame_login.setVisible(false);
                    }
                    else
                    {
                        isuser=true;
                        createSuperMain();
                        jFrame_login.setVisible(false);
                    }
                }
            }
        });
        jFrame_login.setVisible(true);
    }
    void createSuperMain()
    {
        JFrame jFrame_main=new JFrame(username);
        jFrame_main.setLayout(null);
        jFrame_main.setBounds(400,400,500,400);

        JButton btn_shelter=new JButton("收容所信息的查询和维护");
        JButton btn_animal=new JButton("动物信息的查询和维护");
        JButton btn_health=new JButton("健康信息的查询和维护");
        JButton btn_vaccine=new JButton("疫苗信息的查询和维护");
        JButton btn_close=new JButton("退出");
        jFrame_main.add(btn_animal);
        jFrame_main.add(btn_health);
        jFrame_main.add(btn_shelter);
        jFrame_main.add(btn_vaccine);
        jFrame_main.add(btn_close);
        btn_animal.setBounds(100,50,200,30);
        btn_health.setBounds(100,110,200,30);
        btn_shelter.setBounds(100,180,200,30);
        btn_vaccine.setBounds(100,250,200,30);
        btn_close.setBounds(350,310,80,30);
        btn_animal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createanimalsinf();
            }
        });
        btn_health.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createhealthinf();
            }
        });
        btn_shelter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createshelter();
            }
        });
        btn_vaccine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createvaccineinf();
            }
        });
        btn_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLogin();
                jFrame_main.setVisible(false);
            }
        });
        jFrame_main.setVisible(true);
    }
    void createshelter()
    {
        JFrame jFrame_shelter=new JFrame(username);
        jFrame_shelter.setLayout(null);
        jFrame_shelter.setBounds(400,100,1000,700);

        JLabel jLabel_id=new JLabel("收容所id");
        JTextField jTextField_id=new JTextField();
        JLabel jLabel_name=new JLabel("收容所名称");
        JTextField jTextField_name=new JTextField();
        JLabel jLabel_address=new JLabel("地址");
        JTextField jTextField_address=new JTextField();
        JLabel jLabel_email=new JLabel("邮编");
        JTextField jTextField_email=new JTextField();
        JLabel jLabel_rooms=new JLabel("总房间数");
        JTextField jTextField_rooms=new JTextField();
        JLabel jLabel_restrooms=new JLabel("剩余房间数>");
        JTextField jTextField_restrooms=new JTextField();
        JLabel jLabel_remark=new JLabel("备注");
        JTextField jTextField_remark=new JTextField();
        jFrame_shelter.add(jLabel_name);
        jFrame_shelter.add(jLabel_id);
        jFrame_shelter.add(jLabel_address);
        jFrame_shelter.add(jLabel_rooms);
        jFrame_shelter.add(jLabel_email);
        jFrame_shelter.add(jLabel_restrooms);
        jFrame_shelter.add(jLabel_remark);
        jFrame_shelter.add(jTextField_id);
        jFrame_shelter.add(jTextField_name);
        jFrame_shelter.add(jTextField_address);
        jFrame_shelter.add(jTextField_email);
        jFrame_shelter.add(jTextField_rooms);
        jFrame_shelter.add(jTextField_restrooms);
        jFrame_shelter.add(jTextField_remark);

        jLabel_id.setBounds(50,50,100,30);
        jTextField_id.setBounds(200,50,200,30);
        jLabel_name.setBounds(50,120,100,30);
        jTextField_name.setBounds(200,120,200,30);
        jLabel_address.setBounds(50,190,100,30);
        jTextField_address.setBounds(200,190,200,30);
        jLabel_email.setBounds(50,260,100,30);
        jTextField_email.setBounds(200,260,200,30);
        jLabel_rooms.setBounds(50,330,100,30);
        jTextField_rooms.setBounds(200,330,200,30);
        jLabel_restrooms.setBounds(50,400,100,30);
        jTextField_restrooms.setBounds(200,400,200,30);
        jLabel_remark.setBounds(50,470,100,30);
        jTextField_remark.setBounds(200,470,200,30);

        String [][]data={};
        String []title={"收容所id","收容所名称","地址","邮编","总房间数","剩余房间数","备注"};
        DefaultTableModel model=new DefaultTableModel(data,title);
        JTable jTable=new JTable(model);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> cms = jTable.getColumnModel().getColumns();
        while(cms.hasMoreElements()){
            cms.nextElement().setMaxWidth(100);
        }
        JScrollPane jScrollPane=new JScrollPane(jTable);
        jScrollPane.setBounds(450,50, 500, 500);
        jFrame_shelter.add(jScrollPane);

        JButton btn_query=new JButton("查询");
        JButton btn_insert=new JButton("插入");
        JButton btn_update=new JButton("更改");
        JButton btn_delete=new JButton("删除");
        if(isuser)
        {
            btn_insert.setEnabled(false);
            btn_update.setEnabled(false);
            btn_delete.setEnabled(false);
        }
        btn_insert.setBounds(50,560,60,30);
        btn_query.setBounds(140,560,60,30);
        btn_update.setBounds(230,560,60,30);
        btn_delete.setBounds(320,560,60,30);
        jFrame_shelter.add(btn_query);
        jFrame_shelter.add(btn_insert);
        jFrame_shelter.add(btn_update);
        jFrame_shelter.add(btn_delete);
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String address=jTextField_address.getText();
                String email=jTextField_email.getText();
                String rooms=jTextField_rooms.getText();
                String restrooms=jTextField_restrooms.getText();
                String remarks=jTextField_remark.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("收容所id='"+id+"'");
                }
                if(!name.equals(""))
                {
                    data.add("收容所名称='"+name+"'");
                }
                if(!address.equals(""))
                {
                    data.add("地址='"+address+"'");
                }
                if(!email.equals(""))
                {
                    data.add("邮编='"+email+"'");
                }
                if(!rooms.equals(""))
                {
                    data.add("总房间数>"+rooms);
                }
                if(!restrooms.equals(""))
                {
                    data.add("剩余房间数>"+restrooms);
                }
                if(connectOracle.delete(2,data))
                    JOptionPane.showMessageDialog(null, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btn_insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String address=jTextField_address.getText();
                String email=jTextField_email.getText();
                String rooms=jTextField_rooms.getText();
                String restrooms=jTextField_restrooms.getText();
                String remarks=jTextField_remark.getText();
                if(id.equals("")||name.equals("")||address.equals("")||email.equals("")||rooms.equals("")||restrooms.equals(""))
                    JOptionPane.showMessageDialog(null, "请将插入信息填写完整！（只允许备注为空）", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    ArrayList<String> data=new ArrayList<>();
                    data.add(id);
                    data.add(name);
                    data.add(address);
                    data.add(email);
                    data.add(rooms);
                    data.add(restrooms);
                    data.add(remarks);
                    if(connectOracle.insert(2,data))
                        JOptionPane.showMessageDialog(null, "插入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "插入失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btn_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String address=jTextField_address.getText();
                String email=jTextField_email.getText();
                String rooms=jTextField_rooms.getText();
                String restrooms=jTextField_restrooms.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("收容所id='"+id+"'");
                }
                if(!name.equals(""))
                {
                    data.add("收容所名称='"+name+"'");
                }
                if(!address.equals(""))
                {
                    data.add("地址='"+address+"'");
                }
                if(!email.equals(""))
                {
                    data.add("邮编='"+email+"'");
                }
                if(!rooms.equals(""))
                {
                    data.add("总房间数>"+rooms);
                }
                if(!restrooms.equals(""))
                {
                    data.add("剩余房间数>"+restrooms);
                }
                ArrayList<String> res=connectOracle.query(2,data);
                for(int i=0;i<res.size();i++)
                {
                    String []temp=res.get(i).split(" ");
                    model.addRow(temp);
                }
            }
        });
        btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String address=jTextField_address.getText();
                String email=jTextField_email.getText();
                String rooms=jTextField_rooms.getText();
                String restrooms=jTextField_restrooms.getText();
                String remarks=jTextField_remark.getText();
                HashMap<String,String> data=new HashMap<>();
                if(id.equals(""))
                    JOptionPane.showMessageDialog(null, "更新时收容所id不能为空!", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    if(!name.equals(""))
                    {
                        data.put("收容所名称",name);
                    }
                    if(!address.equals(""))
                    {
                        data.put("地址",address);
                    }
                    if(!email.equals(""))
                    {
                        data.put("邮编",email);
                    }
                    if(!rooms.equals(""))
                    {
                        data.put("总房间数",rooms);
                    }
                    if(!restrooms.equals(""))
                    {
                        data.put("剩余房间数",restrooms);
                    }
                    if(!remarks.equals(""))
                    {
                        data.put("备注",remarks);
                    }
                    if(connectOracle.update(2,id,data))
                        JOptionPane.showMessageDialog(null, "更新成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "更新失败!", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        jFrame_shelter.setVisible(true);
    }
    void createanimalsinf()
    {
        JFrame jFrame_animals=new JFrame(username);
        jFrame_animals.setLayout(null);
        jFrame_animals.setBounds(400,100,1000,700);

        JLabel jLabel_id=new JLabel("动物id");
        JTextField jTextField_id=new JTextField();
        JLabel jLabel_name=new JLabel("动物名");
        JTextField jTextField_name=new JTextField();
        JLabel jLabel_address=new JLabel("动物编号");
        JTextField jTextField_address=new JTextField();
        JLabel jLabel_email=new JLabel("种类");
        JTextField jTextField_email=new JTextField();
        JLabel jLabel_rooms=new JLabel("性别");
        JTextField jTextField_rooms=new JTextField();
        JLabel jLabel_restrooms=new JLabel("年龄>");
        JTextField jTextField_restrooms=new JTextField();
        JLabel jLabel_remark=new JLabel("图像");
        JTextField jTextField_remark=new JTextField();
        JLabel jLabel_shelter=new JLabel("收容所id");
        JTextField jTextField_shelter=new JTextField();
        jFrame_animals.add(jLabel_name);
        jFrame_animals.add(jLabel_id);
        jFrame_animals.add(jLabel_address);
        jFrame_animals.add(jLabel_rooms);
        jFrame_animals.add(jLabel_email);
        jFrame_animals.add(jLabel_restrooms);
        jFrame_animals.add(jLabel_remark);
        jFrame_animals.add(jTextField_id);
        jFrame_animals.add(jTextField_name);
        jFrame_animals.add(jTextField_address);
        jFrame_animals.add(jTextField_email);
        jFrame_animals.add(jTextField_rooms);
        jFrame_animals.add(jTextField_restrooms);
        jFrame_animals.add(jTextField_remark);
        jFrame_animals.add(jTextField_shelter);
        jFrame_animals.add(jLabel_shelter);

        jLabel_id.setBounds(50,50,100,30);
        jTextField_id.setBounds(200,50,200,30);
        jLabel_name.setBounds(50,120,100,30);
        jTextField_name.setBounds(200,120,200,30);
        jLabel_address.setBounds(50,190,100,30);
        jTextField_address.setBounds(200,190,200,30);
        jLabel_email.setBounds(50,260,100,30);
        jTextField_email.setBounds(200,260,200,30);
        jLabel_rooms.setBounds(50,330,100,30);
        jTextField_rooms.setBounds(200,330,200,30);
        jLabel_restrooms.setBounds(50,400,100,30);
        jTextField_restrooms.setBounds(200,400,200,30);
        jLabel_remark.setBounds(50,470,100,30);
        jTextField_remark.setBounds(200,470,200,30);
        jLabel_shelter.setBounds(50,540,100,30);
        jTextField_shelter.setBounds(200,540,200,30);

        String [][]data={};
        String []title={"动物id","动物名","动物编号","种类","性别","年龄","图像","收容所id"};
        DefaultTableModel model=new DefaultTableModel(data,title);
        JTable jTable=new JTable(model);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> cms = jTable.getColumnModel().getColumns();
        while(cms.hasMoreElements()){
            cms.nextElement().setMaxWidth(100);
        }
        JScrollPane jScrollPane=new JScrollPane(jTable);
        jScrollPane.setBounds(450,50, 500, 500);
        jFrame_animals.add(jScrollPane);

        JButton btn_query=new JButton("查询");
        JButton btn_insert=new JButton("插入");
        JButton btn_update=new JButton("更改");
        JButton btn_delete=new JButton("删除");
        if(isuser)
        {
            btn_insert.setEnabled(false);
            btn_update.setEnabled(false);
            btn_delete.setEnabled(false);
        }
        btn_insert.setBounds(50,600,60,30);
        btn_query.setBounds(140,600,60,30);
        btn_update.setBounds(230,600,60,30);
        btn_delete.setBounds(320,600,60,30);
        jFrame_animals.add(btn_query);
        jFrame_animals.add(btn_insert);
        jFrame_animals.add(btn_update);
        jFrame_animals.add(btn_delete);
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String number=jTextField_address.getText();
                String kind=jTextField_email.getText();
                String sex=jTextField_rooms.getText();
                String age=jTextField_restrooms.getText();
                String image=jTextField_remark.getText();
                String shelterid=jTextField_shelter.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("动物id='"+id+"'");
                }
                if(!name.equals(""))
                {
                    data.add("动物名='"+name+"'");
                }
                if(!number.equals(""))
                {
                    data.add("动物编号='"+number+"'");
                }
                if(!kind.equals(""))
                {
                    data.add("种类='"+kind+"'");
                }
                if(!sex.equals(""))
                {
                    data.add("性别='"+sex+"'");
                }
                if(!age.equals(""))
                {
                    data.add("年龄>"+age);
                }
                if(!shelterid.equals(""))
                {
                    data.add("收容所id='"+shelterid+"'");
                }
                if(connectOracle.delete(1,data))
                    JOptionPane.showMessageDialog(null, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btn_insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String number=jTextField_address.getText();
                String kind=jTextField_email.getText();
                String sex=jTextField_rooms.getText();
                String age=jTextField_restrooms.getText();
                String image=jTextField_remark.getText();
                String shelterid=jTextField_shelter.getText();
                if(id.equals("")||name.equals("")||number.equals("")||kind.equals("")||sex.equals("")||age.equals("")||shelterid.equals(""))
                    JOptionPane.showMessageDialog(null, "请将插入信息填写完整！（只允许图像为空）", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    ArrayList<String> data=new ArrayList<>();
                    data.add(id);
                    data.add(number);
                    data.add(name);
                    data.add(kind);
                    data.add(sex);
                    data.add(age);
                    data.add(image);
                    data.add(shelterid);
                    if(connectOracle.insert(1,data))
                        JOptionPane.showMessageDialog(null, "插入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "插入失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btn_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String number=jTextField_address.getText();
                String kind=jTextField_email.getText();
                String sex=jTextField_rooms.getText();
                String age=jTextField_restrooms.getText();
                String image=jTextField_remark.getText();
                String shelterid=jTextField_shelter.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("动物id='"+id+"'");
                }
                if(!name.equals(""))
                {
                    data.add("动物名='"+name+"'");
                }
                if(!number.equals(""))
                {
                    data.add("动物编号='"+number+"'");
                }
                if(!kind.equals(""))
                {
                    data.add("种类='"+kind+"'");
                }
                if(!sex.equals(""))
                {
                    data.add("性别='"+sex+"'");
                }
                if(!age.equals(""))
                {
                    data.add("年龄>"+age);
                }
                if(!shelterid.equals(""))
                {
                    data.add("收容所id='"+shelterid+"'");
                }
                ArrayList<String> res=connectOracle.query(1,data);
                for(int i=0;i<res.size();i++)
                {
                    String []temp=res.get(i).split(" ");
                    model.addRow(temp);
                }
            }
        });
        btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String name=jTextField_name.getText();
                String number=jTextField_address.getText();
                String kind=jTextField_email.getText();
                String sex=jTextField_rooms.getText();
                String age=jTextField_restrooms.getText();
                String image=jTextField_remark.getText();
                String shelterid=jTextField_shelter.getText();
                HashMap<String,String> data=new HashMap<>();
                if(id.equals(""))
                    JOptionPane.showMessageDialog(null, "更新时动物id不能为空!", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    if(!id.equals(""))
                    {
                        data.put("动物id",id);
                    }
                    if(!name.equals(""))
                    {
                        data.put("动物名",name);
                    }
                    if(!number.equals(""))
                    {
                        data.put("动物编号",number);
                    }
                    if(!kind.equals(""))
                    {
                        data.put("种类",kind);
                    }
                    if(!sex.equals(""))
                    {
                        data.put("性别",sex);
                    }
                    if(!age.equals(""))
                    {
                        data.put("年龄",age);
                    }
                    if(!image.equals(""))
                    {
                        data.put("图像",image);
                    }
                    if(!shelterid.equals(""))
                    {
                        data.put("收容所id",shelterid);
                    }
                    if(connectOracle.update(1,id,data))
                        JOptionPane.showMessageDialog(null, "更新成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "更新失败!", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        jFrame_animals.setVisible(true);
    }
    void createhealthinf()
    {
        JFrame jFrame_health=new JFrame(username);
        jFrame_health.setLayout(null);
        jFrame_health.setBounds(400,100,1000,700);

        JLabel jLabel_id=new JLabel("健康信息id");
        JTextField jTextField_id=new JTextField();
        JLabel jLabel_animal=new JLabel("动物id");
        JTextField jTextField_animal=new JTextField();
        JLabel jLabel_user=new JLabel("用户id");
        JTextField jTextField_user=new JTextField();
        JLabel jLabel_inf=new JLabel("健康信息");
        JTextField jTextField_inf=new JTextField();
        JLabel jLabel_date=new JLabel("检查日期>");
        JTextField jTextField_date=new JTextField();
        JLabel jLabel_remark=new JLabel("备注");
        JTextField jTextField_remark=new JTextField();
        jFrame_health.add(jLabel_animal);
        jFrame_health.add(jLabel_id);
        jFrame_health.add(jLabel_date);
        jFrame_health.add(jLabel_inf);
        jFrame_health.add(jLabel_user);
        jFrame_health.add(jLabel_remark);
        jFrame_health.add(jTextField_id);
        jFrame_health.add(jTextField_animal);
        jFrame_health.add(jTextField_date);
        jFrame_health.add(jTextField_inf);
        jFrame_health.add(jTextField_user);
        jFrame_health.add(jTextField_remark);

        jLabel_id.setBounds(50,50,100,30);
        jTextField_id.setBounds(200,50,200,30);
        jLabel_animal.setBounds(50,120,100,30);
        jTextField_animal.setBounds(200,120,200,30);
        jLabel_user.setBounds(50,190,100,30);
        jTextField_user.setBounds(200,190,200,30);
        jLabel_inf.setBounds(50,260,100,30);
        jTextField_inf.setBounds(200,260,200,30);
        jLabel_date.setBounds(50,330,100,30);
        jTextField_date.setBounds(200,330,200,30);
        jLabel_remark.setBounds(50,400,100,30);
        jTextField_remark.setBounds(200,400,200,30);

        String [][]data={};
        String []title={"健康信息id","动物id","用户id","健康信息","检查日期","备注"};
        DefaultTableModel model=new DefaultTableModel(data,title);
        JTable jTable=new JTable(model);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> cms = jTable.getColumnModel().getColumns();
        while(cms.hasMoreElements()){
            cms.nextElement().setMaxWidth(100);
        }
        JScrollPane jScrollPane=new JScrollPane(jTable);
        jScrollPane.setBounds(450,50, 500, 500);
        jFrame_health.add(jScrollPane);

        JButton btn_query=new JButton("查询");
        JButton btn_insert=new JButton("插入");
        JButton btn_update=new JButton("更改");
        JButton btn_delete=new JButton("删除");
        btn_insert.setBounds(50,560,60,30);
        btn_query.setBounds(140,560,60,30);
        btn_update.setBounds(230,560,60,30);
        btn_delete.setBounds(320,560,60,30);
        jFrame_health.add(btn_query);
        jFrame_health.add(btn_insert);
        jFrame_health.add(btn_update);
        jFrame_health.add(btn_delete);
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("健康信息id='"+id+"'");
                }
                if(!animalid.equals(""))
                {
                    data.add("动物id='"+animalid+"'");
                }
                if(!shelterid.equals(""))
                {
                    data.add("用户id='"+shelterid+"'");
                }
                if(!inf.equals(""))
                {
                    data.add("健康信息='"+inf+"'");
                }
                if(!date.equals(""))
                {
                    data.add("检查日期>to_date('"+date+"','yyyy-mm-dd')");
                }
                if(connectOracle.delete(3,data))
                    JOptionPane.showMessageDialog(null, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btn_insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                if(id.equals("")||animalid.equals("")||shelterid.equals("")||inf.equals("")||date.equals(""))
                    JOptionPane.showMessageDialog(null, "请将插入信息填写完整！（只允许备注为空）", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    ArrayList<String> data=new ArrayList<>();
                    data.add(id);
                    data.add(animalid);
                    data.add(shelterid);
                    data.add(inf);
                    data.add(date);
                    data.add(remark);
                    if(connectOracle.insert(3,data))
                        JOptionPane.showMessageDialog(null, "插入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "插入失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btn_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("健康信息id='"+id+"'");
                }
                if(!animalid.equals(""))
                {
                    data.add("动物id='"+animalid+"'");
                }
                if(!shelterid.equals(""))
                {
                    data.add("用户id='"+shelterid+"'");
                }
                if(!inf.equals(""))
                {
                    data.add("健康信息='"+inf+"'");
                }
                if(!date.equals(""))
                {
                    data.add("检查日期>to_date('"+date+"','yyyy-mm-dd')");
                }
                ArrayList<String> res=connectOracle.query(3,data);
                for(int i=0;i<res.size();i++)
                {
                    String []temp=res.get(i).split(" ");
                    model.addRow(temp);
                }
            }
        });
        btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                HashMap<String,String> data=new HashMap<>();
                if(id.equals(""))
                    JOptionPane.showMessageDialog(null, "更新时动物id不能为空!", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    if(!id.equals(""))
                    {
                        data.put("健康信息id",id);
                    }
                    if(!animalid.equals(""))
                    {
                        data.put("动物id",animalid);
                    }
                    if(!shelterid.equals(""))
                    {
                        data.put("用户id",shelterid);
                    }
                    if(!inf.equals(""))
                    {
                        data.put("健康信息",inf);
                    }
                    if(!date.equals(""))
                    {
                        data.put("检查日期",date);
                    }
                    if(!remark.equals(""))
                    {
                        data.put("备注",remark);
                    }
                    if(connectOracle.update(3,id,data))
                        JOptionPane.showMessageDialog(null, "更新成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "更新失败!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }}
        });
        jFrame_health.setVisible(true);
    }
    void createvaccineinf()
    {
        JFrame jFrame_health=new JFrame(username);
        jFrame_health.setLayout(null);
        jFrame_health.setBounds(400,100,1000,700);

        JLabel jLabel_id=new JLabel("疫苗信息id");
        JTextField jTextField_id=new JTextField();
        JLabel jLabel_animal=new JLabel("动物id");
        JTextField jTextField_animal=new JTextField();
        JLabel jLabel_user=new JLabel("用户id");
        JTextField jTextField_user=new JTextField();
        JLabel jLabel_inf=new JLabel("疫苗名");
        JTextField jTextField_inf=new JTextField();
        JLabel jLabel_date=new JLabel("接种日期>");
        JTextField jTextField_date=new JTextField();
        JLabel jLabel_remark=new JLabel("备注");
        JTextField jTextField_remark=new JTextField();
        jFrame_health.add(jLabel_animal);
        jFrame_health.add(jLabel_id);
        jFrame_health.add(jLabel_date);
        jFrame_health.add(jLabel_inf);
        jFrame_health.add(jLabel_user);
        jFrame_health.add(jLabel_remark);
        jFrame_health.add(jTextField_id);
        jFrame_health.add(jTextField_animal);
        jFrame_health.add(jTextField_date);
        jFrame_health.add(jTextField_inf);
        jFrame_health.add(jTextField_user);
        jFrame_health.add(jTextField_remark);

        jLabel_id.setBounds(50,50,100,30);
        jTextField_id.setBounds(200,50,200,30);
        jLabel_animal.setBounds(50,120,100,30);
        jTextField_animal.setBounds(200,120,200,30);
        jLabel_user.setBounds(50,190,100,30);
        jTextField_user.setBounds(200,190,200,30);
        jLabel_inf.setBounds(50,260,100,30);
        jTextField_inf.setBounds(200,260,200,30);
        jLabel_date.setBounds(50,330,100,30);
        jTextField_date.setBounds(200,330,200,30);
        jLabel_remark.setBounds(50,400,100,30);
        jTextField_remark.setBounds(200,400,200,30);

        String [][]data={};
        String []title={"疫苗信息id","动物id","用户id","疫苗名","接种日期","备注"};
        DefaultTableModel model=new DefaultTableModel(data,title);
        JTable jTable=new JTable(model);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> cms = jTable.getColumnModel().getColumns();
        while(cms.hasMoreElements()){
            cms.nextElement().setMaxWidth(100);
        }
        JScrollPane jScrollPane=new JScrollPane(jTable);
        jScrollPane.setBounds(450,50, 500, 500);
        jFrame_health.add(jScrollPane);

        JButton btn_query=new JButton("查询");
        JButton btn_insert=new JButton("插入");
        JButton btn_update=new JButton("更改");
        JButton btn_delete=new JButton("删除");
        btn_insert.setBounds(50,560,60,30);
        btn_query.setBounds(140,560,60,30);
        btn_update.setBounds(230,560,60,30);
        btn_delete.setBounds(320,560,60,30);
        jFrame_health.add(btn_query);
        jFrame_health.add(btn_insert);
        jFrame_health.add(btn_update);
        jFrame_health.add(btn_delete);
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("疫苗信息id='"+id+"'");
                }
                if(!animalid.equals(""))
                {
                    data.add("动物id='"+animalid+"'");
                }
                if(!shelterid.equals(""))
                {
                    data.add("用户id='"+shelterid+"'");
                }
                if(!inf.equals(""))
                {
                    data.add("疫苗名='"+inf+"'");
                }
                if(!date.equals(""))
                {
                    data.add("接种日期>to_date('"+date+"','yyyy-mm-dd')");
                }
                if(connectOracle.delete(4,data))
                    JOptionPane.showMessageDialog(null, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btn_insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                if(id.equals("")||animalid.equals("")||shelterid.equals("")||inf.equals("")||date.equals(""))
                    JOptionPane.showMessageDialog(null, "请将插入信息填写完整！（只允许备注为空）", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    ArrayList<String> data=new ArrayList<>();
                    data.add(id);
                    data.add(animalid);
                    data.add(shelterid);
                    data.add(inf);
                    data.add(date);
                    data.add(remark);
                    if(connectOracle.insert(4,data))
                        JOptionPane.showMessageDialog(null, "插入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "插入失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btn_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                ArrayList<String> data=new ArrayList<>();
                if(!id.equals(""))
                {
                    data.add("疫苗信息id='"+id+"'");
                }
                if(!animalid.equals(""))
                {
                    data.add("动物id='"+animalid+"'");
                }
                if(!shelterid.equals(""))
                {
                    data.add("用户id='"+shelterid+"'");
                }
                if(!inf.equals(""))
                {
                    data.add("疫苗名='"+inf+"'");
                }
                if(!date.equals(""))
                {
                    data.add("接种日期>to_date('"+date+"','yyyy-mm-dd')");
                }
                ArrayList<String> res=connectOracle.query(4,data);
                for(int i=0;i<res.size();i++)
                {
                    String []temp=res.get(i).split(" ");
                    model.addRow(temp);
                }
            }
        });
        btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=jTextField_id.getText();
                String animalid=jTextField_animal.getText();
                String shelterid=jTextField_user.getText();
                String inf=jTextField_inf.getText();
                String date=jTextField_date.getText();
                String remark=jTextField_remark.getText();
                HashMap<String,String> data=new HashMap<>();
                if(id.equals(""))
                    JOptionPane.showMessageDialog(null, "更新时动物id不能为空!", "提示", JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    if(!id.equals(""))
                    {
                        data.put("疫苗信息id",id);
                    }
                    if(!animalid.equals(""))
                    {
                        data.put("动物id",animalid);
                    }
                    if(!shelterid.equals(""))
                    {
                        data.put("用户id",shelterid);
                    }
                    if(!inf.equals(""))
                    {
                        data.put("疫苗名",inf);
                    }
                    if(!date.equals(""))
                    {
                        data.put("接种日期",date);
                    }
                    if(!remark.equals(""))
                    {
                        data.put("备注",remark);
                    }
                    if(connectOracle.update(4,id,data))
                        JOptionPane.showMessageDialog(null, "更新成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "更新失败!", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        jFrame_health.setVisible(true);
    }
    public static void main(String []args)
    {
        Main m=new Main();
        m.connectOracle.init() ;
        Runnable runnable = new Runnable() {
            public void run() {
                m.connectOracle.updateanimalage();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
        m.createLogin();
    }
}
