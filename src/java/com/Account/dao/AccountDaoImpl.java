/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Account.dao;

import com.Account.bean.Account;
import com.Account.bean.CurrentAccount;
import com.Account.bean.SavingsAccount;
import com.Client.bean.Client;
import com.cards.bean.Card;
import com.currency.bean.Currency;
import com.login.dao.LoginDaoImpl;
import connexion.util.SingletonConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class AccountDaoImpl implements AccountDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public int AddAccount(Account account) {
    int id = 0;
    String query = "INSERT INTO public.account\n"
            + "(accountname, balance, ownerid,"
            + " currency, accountstate,"
            + " transferlimit, accounttype, creationdate, rib, iban)\n"
            + "VALUES(?, ?, ?, ?, ?, ?,?, now(), "
            + " upper(?), ?"
            + ") returning id;";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, account.getName());
      preparedStatement.setBigDecimal(2, account.getBalance());
      preparedStatement.setInt(3, account.getOwnerID());
      preparedStatement.setString(4, account.getCurrency().getCode());

      preparedStatement.setString(5, account.getAccountState());
      preparedStatement.setBigDecimal(6, account.getTransferLimit());
      preparedStatement.setString(7, account.getAccountType());
      preparedStatement.setString(8, account.getRIB());
      preparedStatement.setString(9, account.getIBAN());
      ResultSet resultset = preparedStatement.executeQuery();
      
      if (resultset.next()) {
        id = resultset.getInt("id");
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return id;
  }

  @Override
  public String AddSavingsAccount(SavingsAccount account) {
    try {

      String savingQuery = "INSERT INTO savings_account\n"
              + "(id, saving_goal)\n"
              + "VALUES(?, ?);";

      PreparedStatement preparedStatementSavings = connexion.prepareStatement(savingQuery);
      preparedStatementSavings.setInt(1, account.getId());
      preparedStatementSavings.setBigDecimal(2, account.getSavingGoal());

      int rs = preparedStatementSavings.executeUpdate();

      if (rs == 1) {
        return "registred";
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.register";
  }

  @Override
  public String AddCurrentAccount(CurrentAccount account) {

    String checkingQuery = "INSERT INTO checking_account\n"
            + "(id, budget, \"period\")\n"
            + "VALUES(?, ?, ?);";
    try {
      PreparedStatement preparedStatementChecking = connexion.prepareStatement(checkingQuery);
      preparedStatementChecking.setInt(1, account.getId());
      preparedStatementChecking.setBigDecimal(2, account.getBudget());
      preparedStatementChecking.setString(3, account.getPeriod());

      int rs = preparedStatementChecking.executeUpdate();

      if (rs == 1) {
        return "registred";
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.register";
  }

  @Override
  public List<Account> listAccounts(Client user) {
    String query = "SELECT id, accountname, balance,"
            + " ownerid, currency, accountstate,"
            + " transferlimit, accounttype,"
            + " creationdate, rib, iban"
            + " FROM public.account where ownerid=?;";

    List<Account> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setLong(1, user.getId());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        if (rs.getString("accounttype").equals("saving")) {
          SavingsAccount saving = new SavingsAccount();
          saving.setId(rs.getInt("id"));
          saving.setName(rs.getString("accountname"));
          saving.setBalance(rs.getBigDecimal("balance"));
          saving.setOwnerID(rs.getInt("ownerid"));
          saving.setCurrency(getCurrency(rs.getString("currency")));
          saving.setAccountState(rs.getString("accountstate"));
          saving.setTransferLimit(rs.getBigDecimal("transferlimit"));
          saving.setAccountType(rs.getString("accounttype"));
          saving.setCreationDate(rs.getString("creationdate"));
          saving.setRIB(rs.getString("rib"));
          saving.setIBAN(rs.getString("iban"));

          String savingsQuery = "SELECT saving_goal\n"
                  + "FROM savings_account where id=?;";
          PreparedStatement psSavings = connexion.prepareStatement(savingsQuery);
          psSavings.setInt(1, rs.getInt("id"));
          ResultSet rsSavings = psSavings.executeQuery();
          if (rsSavings.next()) {
            saving.setSavingGoal(rsSavings.getBigDecimal("saving_goal"));
          }
          l.add(saving);
        } else if (rs.getString("accounttype").equals("checking")) {
          CurrentAccount current = new CurrentAccount();

          current.setId(rs.getInt("id"));
          current.setName(rs.getString("accountname"));
          current.setBalance(rs.getBigDecimal("balance"));
          current.setOwnerID(rs.getInt("ownerid"));
          current.setCurrency(getCurrency(rs.getString("currency")));
          current.setAccountState(rs.getString("accountstate"));
          current.setTransferLimit(rs.getBigDecimal("transferlimit"));
          current.setAccountType(rs.getString("accounttype"));
          current.setCreationDate(rs.getString("creationdate"));
          current.setRIB(rs.getString("rib"));
          current.setIBAN(rs.getString("iban"));

          String currentQuery = "SELECT budget, \"period\"\n"
                  + "FROM checking_account where id=?;";

          PreparedStatement psChecking = connexion.prepareStatement(currentQuery);
          psChecking.setInt(1, rs.getInt("id"));
          ResultSet rsChecking = psChecking.executeQuery();
          if (rsChecking.next()) {
            current.setBudget(rsChecking.getBigDecimal("budget"));
            current.setPeriod(rsChecking.getString("period"));
          }

          l.add(current);
        }

      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e);

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return l;
  }

  @Override
  public String DeleteAccount(int id) {
    String query = "DELETE From public.account where id=? ";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      int rs = preparedStatement.executeUpdate();

      if (rs == 1) {
        return "deleted";
      }

    } catch (SQLException ex) {
      Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.register";
  }

  @Override
  public String DeleteSavingsAccount(int id) {
    String query = "DELETE From public.savings_account where id=? ";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      int rs = preparedStatement.executeUpdate();

      if (rs == 1) {
        return "deleted";
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.register";
  }

  @Override
  public String DeleteCurrentAccount(int id) {
    String query = "DELETE From public.checking_account where id=? ";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      int rs = preparedStatement.executeUpdate();

      if (rs == 1) {
        return "deleted";
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.register";
  }

  @Override
  public String EditAccount(Account account) {
    String query = "UPDATE public.account\n"
            + "SET accountname=?, currency=?, accounttype=? "
            + "WHERE id=?;";

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(4, account.getId());
      preparedStatement.setString(1, account.getName());
      preparedStatement.setString(2, account.getCurrency().getCode());
      preparedStatement.setString(3, account.getAccountType());

      int rs = preparedStatement.executeUpdate();

      if (rs == 1) {
        return "edited";
      }

    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.edit";
  }

  @Override
  public String EditSavingsAccount(SavingsAccount account) {
    try {
      String savingsQuery = "UPDATE savings_account\n"
              + "SET saving_goal=" + account.getSavingGoal() + "\n"
              + "WHERE id=" + account.getId() + ";";
      PreparedStatement preparedStatementSavings = connexion.prepareStatement(savingsQuery);

      int Savingsrs = preparedStatementSavings.executeUpdate();
      if (Savingsrs == 1) {
        return "edited";
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.edit";
  }

  @Override
  public String EditCurrentAccount(CurrentAccount account) {
    try {

      String savingsQuery = "UPDATE checking_account\n"
              + "SET budget=?, \"period\"=?\n"
              + "WHERE id=?;";
      PreparedStatement preparedStatementCurrent = connexion.prepareStatement(savingsQuery);
      preparedStatementCurrent.setBigDecimal(1, account.getBudget());
      preparedStatementCurrent.setString(2, account.getPeriod());
      preparedStatementCurrent.setInt(3, account.getId());
      int currentrs = preparedStatementCurrent.executeUpdate();
      if (currentrs == 1) {
        return "edited";
      }

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "error.register";
  }

  @Override
  public Account getAccount(String id) {
    String query = "SELECT id, accountname, balance,"
            + " ownerid, currency, accountstate,"
            + " transferlimit, accounttype,"
            + " creationdate, rib "
            + " FROM public.account where rib=?;";

    Account compte = new Account();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        compte.setId(rs.getInt("id"));
        compte.setName(rs.getString("accountname"));
        compte.setBalance(rs.getBigDecimal("balance"));
        compte.setOwnerID(rs.getInt("ownerid"));
        compte.setCurrency(getCurrency(rs.getString("currency")));
        compte.setAccountState(rs.getString("accountstate"));
        compte.setTransferLimit(rs.getBigDecimal("transferlimit"));
        compte.setAccountType(rs.getString("accounttype"));
        compte.setCreationDate(rs.getString("creationdate"));
        compte.setRIB(rs.getString("rib"));
        return compte;
      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return null;
  }

  @Override
  public String Deposit(int id, BigDecimal amount) {
    String query = "UPDATE public.account "
            + "set balance=balance + " + amount
            + " WHERE id=?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public String Withdraw(int id, BigDecimal amount) {
    String query = "UPDATE public.account "
            + "set balance = balance - " + amount
            + " WHERE id = ?;";
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }
    return "SUCCES";
  }

  @Override
  public List<Card> listCards(int idAccount) {
    String query = "select "
            + "cardnumber, pin,"
            + " creationdate,"
            + " expirationdate,"
            + " cardstate,"
            + " withdrawlimit,"
            + " holdername, cvv, id, idaccount, to_char(expirationdate,'yy') as year, to_char(expirationdate,'mm') as month "
            + "from card where idaccount=? and cardstate='active';";

    List<Card> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setLong(1, idAccount);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Card card = new Card();
        card.setCardNumber(rs.getString("cardnumber"));
        card.setCardState(rs.getString("cardstate"));
        card.setCreationDate(rs.getString("creationdate"));
        String date = rs.getString("month") + " / " + rs.getString("year");
        card.setExpirationDate(date);
        card.setHolderName(rs.getString("holdername"));
        card.setPIN(rs.getString("pin"));
        card.setWithdrawLimit(rs.getBigDecimal("withdrawlimit"));
        card.setIdAccount(rs.getString("idaccount"));
        card.setCVV(rs.getInt("cvv"));
        l.add(card);

      }
    } catch (SQLException e) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e);

      try {
        if (connexion != null) {

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return l;
  }

  @Override
  public Currency getCurrency(String code) {
    String query = "SELECT code, name, symbol"
            + " FROM public.currency where code=?;";

    Currency currency = new Currency();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, code);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        currency.setCode(rs.getString("code"));
        currency.setName(rs.getString("name"));
        currency.setSymbol(rs.getString("symbol"));

      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return currency;
  }

  @Override
  public List<Currency> getCurrencies() {
    String query = "SELECT code, name, symbol"
            + " FROM public.currency";

    List<Currency> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Currency currency = new Currency();
        currency.setCode(rs.getString("code"));
        currency.setName(rs.getString("name"));
        currency.setSymbol(rs.getString("symbol"));
        l.add(currency);
      }

    } catch (SQLException e) {
      try {
        if (connexion != null) {
          Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e);

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return l;
  }

  @Override
  public Account getAccountById(int id) {
    String query = "SELECT id, accountname, balance,"
            + " ownerid, currency, accountstate,"
            + " transferlimit, accounttype,"
            + " creationdate, rib\n"
            + " FROM public.account where id=?;";

    Account compte = new Account();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setLong(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        compte.setId(rs.getInt("id"));
        compte.setName(rs.getString("accountname"));
        compte.setBalance(rs.getBigDecimal("balance"));
        compte.setOwnerID(rs.getInt("ownerid"));
        compte.setCurrency(getCurrency(rs.getString("currency")));
        compte.setAccountState(rs.getString("accountstate"));
        compte.setTransferLimit(rs.getBigDecimal("transferlimit"));
        compte.setAccountType(rs.getString("accounttype"));
        compte.setCreationDate(rs.getString("creationdate"));
        compte.setRIB(rs.getString("rib"));

      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return compte;
  }

  @Override
  public Account getAccountByIBAN(String IBAN) {
    String query = "SELECT id, accountname, balance,"
            + " ownerid, currency, accountstate,"
            + " transferlimit, accounttype,"
            + " creationdate, rib , iban"
            + " FROM public.account where iban=?;";

    Account compte = new Account();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, IBAN);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        compte.setId(rs.getInt("id"));
        compte.setName(rs.getString("accountname"));
        compte.setBalance(rs.getBigDecimal("balance"));
        compte.setOwnerID(rs.getInt("ownerid"));
        compte.setCurrency(getCurrency(rs.getString("currency")));
        compte.setAccountState(rs.getString("accountstate"));
        compte.setTransferLimit(rs.getBigDecimal("transferlimit"));
        compte.setAccountType(rs.getString("accounttype"));
        compte.setCreationDate(rs.getString("creationdate"));
        compte.setRIB(rs.getString("rib"));
        compte.setIBAN(rs.getString("iban"));
        return compte;
      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return null;
  }

}
