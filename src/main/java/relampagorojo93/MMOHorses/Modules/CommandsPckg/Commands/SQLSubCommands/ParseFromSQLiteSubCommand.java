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
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.MySQLConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.MySQLDatabase;
import relampagorojo93.MMOHorses.API.MMOHorsesAPI;
import relampagorojo93.MMOHorses.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingString;

public class ParseFromSQLiteSubCommand extends SubCommand {
	public ParseFromSQLiteSubCommand(Command command) {
		super(command, "parsefromsqlite", SettingString.HORSESQL_PARSEFROMSQLITE_NAME.toString(),
				SettingString.HORSESQL_PARSEFROMSQLITE_PERMISSION.toString(),
				SettingString.HORSESQL_PARSEFROMSQLITE_DESCRIPTION.toString(),
				"",
				SettingList.HORSESQL_PARSEFROMSQLITE_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (MMOHorsesAPI.getSQLModule().getType() == SQLType.SQLITE) {
			TasksUtils.executeOnAsync(MMOHorsesAPI.getPlugin(), () -> {
				SQLObject dest = new SQLObject();
				if (dest.request(
						new MySQLConnectionData(SettingString.PROTOCOL.toString(), SettingString.HOST.toString(),
								SettingInt.PORT.toInt(), SettingString.DATABASE.toString(), SettingString.USERNAME.toString(),
								SettingString.PASSWORD.toString(), SettingString.PARAMETERS.toString().split("&")))) {
					if (MMOHorsesAPI.getSQLModule().parseData(dest, new MySQLDatabase(SettingString.TABLEPREFIX.toString())))
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.PARSEDDATA)).sendMessage(sender);
					else
						MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.PARSINGERROR)).sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.SQLITECONNECTIONERROR)).sendMessage(sender);
			});
		} else
			MessagesUtils.getMessageBuilder().createMessage(MMOHorsesAPI.getUtils().applyPrefix(MessageString.MYSQLENABLEDERROR)).sendMessage(sender);
		return true;
	}
}
