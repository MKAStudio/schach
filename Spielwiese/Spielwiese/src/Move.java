import java.util.Objects;

public class Move {
    private boolean AddFigure;
    private boolean isWhite;
    private String moveStr;
    private String figure;
    private PositionCoordinate firstPosition;
    private PositionCoordinate lastPosition;
    private boolean isHitFigure = false;

    //King
    private boolean isCastling = false;
    private boolean isShortCastling;
    
    //Pawn
    private boolean isEn = false;
    private boolean isDoubleMove = false;
    private String pawnConversion = null;
    
    public Move(String moveStr, String figure, PositionCoordinate firstPosition, PositionCoordinate lastPosition) {
        this.moveStr = moveStr;
        this.figure = figure;
        AddFigure = false;
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
    }

    public boolean isAddFigure() {
        return AddFigure;
    }

    public String getMoveStr() {
        return moveStr;
    }
    public void setMoveStr(String moveStr) {
    	this.moveStr = moveStr;
    }

    public String getFigureStr() { //== getFigure()
        return figure;
    }
    
    public PositionCoordinate getFirstPosition() {
        return firstPosition;
    }

    public PositionCoordinate getLastPosition() {
        return lastPosition;
    }

    public Move(boolean addFigure, boolean isWhite, String moveStr, String figure, PositionCoordinate firstPosition,
			PositionCoordinate lastPosition, boolean isHitFigure, boolean isCastling, boolean isShortCastling,
			boolean isEn, boolean isDoubleMove, String pawnConversion) {
		super();
		AddFigure = addFigure;
		this.isWhite = isWhite;
		this.moveStr = moveStr;
		this.figure = figure;
		this.firstPosition = firstPosition;
		this.lastPosition = lastPosition;
		this.isHitFigure = isHitFigure;
		this.isCastling = isCastling;
		this.isShortCastling = isShortCastling;
		this.isEn = isEn;
		this.isDoubleMove = isDoubleMove;
		this.pawnConversion = pawnConversion;
	}

	@Override
    public String toString() {
        return "Move{" +
                "AddFigure=" + AddFigure +
                ", isWhite='" + isWhite + '\'' +
                ", moveStr='" + moveStr + '\'' +
                ", figure='" + figure + '\'' +
                ", firstPosition=" + firstPosition + '\'' +
                ", lastPosition=" + lastPosition + '\'' +
                ", isHitFigure=" + isHitFigure + '\'' +
                ", isEn=" + isEn + '\'' +
                ", isCastling=" + isCastling + '\'' +
                ", pawnConversion=" + pawnConversion + '\'' +
                ", isDoubleMove=" + isDoubleMove +
                '}';
    }

    @Override
	public int hashCode() {
		return Objects.hash(AddFigure, figure, firstPosition, isCastling, isDoubleMove, isEn, isHitFigure,
				isShortCastling, isWhite, lastPosition, moveStr, pawnConversion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		return AddFigure == other.AddFigure && Objects.equals(figure, other.figure)
				&& Objects.equals(firstPosition, other.firstPosition) && isCastling == other.isCastling
				&& isDoubleMove == other.isDoubleMove && isEn == other.isEn && isHitFigure == other.isHitFigure
				&& isShortCastling == other.isShortCastling && isWhite == other.isWhite
				&& Objects.equals(lastPosition, other.lastPosition) && Objects.equals(moveStr, other.moveStr)
				&& Objects.equals(pawnConversion, other.pawnConversion);
	}

	public Move(String moveStr, String figure, boolean isWhite, PositionCoordinate firstPosition) {
        this.figure = figure;
        this.moveStr = moveStr;
        this.AddFigure = true;
        this.firstPosition = firstPosition;
        this.isWhite = isWhite;
    }
    
    public Move(String moveStr, String figure, PositionCoordinate firstPosition, PositionCoordinate lastPosition, boolean isHitFigure) {
        this.moveStr = moveStr;
        this.figure = figure;
        AddFigure = false;
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
        this.isHitFigure = isHitFigure;
        this.isEn = false;
    }
    
    public Move(String moveStr, String figure, PositionCoordinate firstPosition, PositionCoordinate lastPosition, boolean isHitFigure, boolean isEn, boolean isDoubleMove, String pawnConversion) {
        //Pawn
    	this.moveStr = moveStr;
        this.figure = figure;
        AddFigure = false;
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
        this.isHitFigure = isHitFigure;
        this.isEn = isEn;
        this.isDoubleMove = isDoubleMove;
        this.pawnConversion = pawnConversion;
    }
    

	public boolean isHitFigure() {
		return isHitFigure;
	}
	
	public void setIsCastling(boolean isCastling, boolean isShortCastling) {
		this.isCastling = isCastling;
		this.isShortCastling = isShortCastling;
	}
	
	public boolean isCastling() {
		return isCastling;
	}
	
	public boolean isShortCastling() {
		return isShortCastling;
	}
	public boolean isEn() {
		return isEn;
	}

	public String getFigure() { //== getFigureStr()
		return figure;
	}

	public boolean isWhite() {
		return isWhite;
	}
	
	public void setPawnConversion(String pawnConversion) {
		if (pawnConversion == null) {pawnConversion = "";}
		this.pawnConversion = pawnConversion;
	}
	
	public String getPawnConversion() {
		if (pawnConversion == null) {System.out.println("PawnConversion = null!"); pawnConversion = "";}
		return this.pawnConversion;
	}
	
	public boolean isDoubleMove() {
		return this.isDoubleMove;
	}

	@Override
	protected Move clone() {
		// TODO Auto-generated method stub
		return new Move(AddFigure, isWhite, moveStr, figure, firstPosition, lastPosition, isHitFigure, isCastling, isShortCastling, isEn, isDoubleMove, pawnConversion);
	}
}
