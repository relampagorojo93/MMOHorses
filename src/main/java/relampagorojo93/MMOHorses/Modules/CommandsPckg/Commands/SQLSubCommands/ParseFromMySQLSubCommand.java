package relampagorojo93.MMOHorses.Modules.CommandsPckg.Commands.SQLSubCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.SQLiteConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.SQLiteDatabase;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class ParseFromMySQLSubCommand extends SubCommand {
	public ParseFromMySQLSubCommand(Command command) {
		super(command, "parsefrommysql", SettingString.HORSESQL_PARSEFROMMYSQL_NAME.toString(),
				SettingString.HORSESQL_PARSEFROMMYSQL_PERMISSION.toString(),
				SettingString.HORSESQL_PARSEFROMMYSQL_DESCRIPTION.toString(),
				"",
				SettingList.HORSESQL_PARSEFROMMYSQL_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (MMOHorsesAPI.getSQLModule().getType() == SQLType.MYSQL) {
			TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(), () -> {
				SQLObject dest = new SQLObject();
				if (dest.request(new SQLiteConnectionData(MMOHorsesAPI.getFileModule().PLUGIN_FOLDER.getPath() + "/DB.sqlite"))) {
					if (MMOHorsesAPI.getSQLModule().parseData(dest, new SQLiteDatabase()))
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
					else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MYSQLCONNECTIONERROR)).sendMessage(sender);
			});
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MYSQLDISABLEDERROR)).sendMessage(sender);
		return true;
	}
}
