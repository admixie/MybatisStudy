package model;

/**
 * 与消息表中对应的javabean
 * 用于保存每个记录的值，方便保存和读取
 */
public class Message {
    private String ID;
    private String COMMAND;
    private String DESCRIPTION;
    private String CONTENT;

    public String getCOMMAND() {
        return COMMAND;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public String getID() {
        return ID;
    }

    public void setCOMMAND(String COMMAND) {
        this.COMMAND = COMMAND;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("记录ID：");
        sb.append(this.ID);
        sb.append("  命令：");
        sb.append(this.COMMAND);
        sb.append("  描述：");
        sb.append(this.DESCRIPTION);
        sb.append("  回复：");
        sb.append(this.CONTENT);
        return sb.toString();
    }
}
