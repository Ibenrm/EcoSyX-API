# EcoSyX
**This is plugin Economy Support API for PaperMC 1.21.4**

<img src="https://github.com/Ibenrm/EcoSyX-API/blob/main/img/2025-05-14_16.47.48.png" alt="EasyLogin" width="50%">

## Support
- - Custom type money (default: Rp)
- - Custom permission in Setting.yml
- - Custom first login get money (default : 1000)
- - Custom Language (Default: id)
- - Only use database money.yaml (next update support MySQL)

## Warning
**NOTE:** This plugin beta version 0.0.1 if Bugs or Error you can give pull request or issues

## Commands
- Two Model Command
`/economy` `/money` | Aliases Comamnd

- You Money
`/money` | Check you money
- See Money
`/money see <userName>` | Check other Money player
- Pay Money
`/money pay <userName> <amount>` | Pay money to other player
- Add Money
`/money add <userName> <amount>` | Operator add money to other player
- Set Money
`/money add <userName> <amount>` | Operator setting money to other player
- Reduce Money
`/money add <userName> <amount>` | Operator Reduce money to other player

## API
- See Money 
```java
String[] result = EcoSystem.getInstance().getMoney(player.getUniqueId().toString());
```

- Pay Money
```java
String[] result = EcoSystem.getInstance().payMoney(fromPlayer.getUniqueId().toString(), toPlayer.getUniqueId().toString(), amount);
```

- Add Money
```java
String[] result = EcoSystem.getInstance().giveMoney(target.getUniqueId().toString(), amount);
```

- Set Money
```java
String[] result = EcoSystem.getInstance().setMoney(target.getUniqueId().toString(), amount);
```

- Reduce Money
```java
String[] result = EcoSystem.getInstance().reduceMoney(target.getUniqueId().toString(), amount);
```
