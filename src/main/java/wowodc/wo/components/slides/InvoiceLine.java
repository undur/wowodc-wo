package wowodc.wo.components.slides;

/**
 * One line on the invoice slide. The text fields bind to the String accessors, since a WOTextField's value is a String and
 * key-value coding doesn't convert one to an int.
 */

public class InvoiceLine {

	private final int _id;
	private final String _product;
	private int _quantity;
	private int _unitPrice;

	public InvoiceLine( final int id, final String product, final int quantity, final int unitPrice ) {
		_id = id;
		_product = product;
		_quantity = quantity;
		_unitPrice = unitPrice;
	}

	public int id() {
		return _id;
	}

	public String product() {
		return _product;
	}

	public String quantityField() {
		return String.valueOf( _quantity );
	}

	public void setQuantityField( final String value ) {
		_quantity = parseOrZero( value );
	}

	public String unitPriceField() {
		return String.valueOf( _unitPrice );
	}

	public void setUnitPriceField( final String value ) {
		_unitPrice = parseOrZero( value );
	}

	public int lineTotal() {
		return _quantity * _unitPrice;
	}

	private static int parseOrZero( final String value ) {
		try {
			return value == null ? 0 : Integer.parseInt( value.trim() );
		}
		catch( final NumberFormatException e ) {
			return 0;
		}
	}
}
