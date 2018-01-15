
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CreditCardTest 
{
    CreditCards creditcards = new CreditCards();
    CreditCard creditcard = new CreditCard();
    
    @Test
    public void primarykey()
    {
        int primarykey = creditcards.getNextPrimaryKey();
        assertEquals(131, primarykey);
    }
    
    
    
    
}
