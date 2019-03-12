package controller.commands;
import java.util.List;

public interface ICommand {
	
	public void execute();
	public void setParams(List<Object> params);

}
