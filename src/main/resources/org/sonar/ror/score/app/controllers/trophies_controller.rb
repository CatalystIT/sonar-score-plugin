class TrophiesController < ApplicationController
  
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  
  verify :method => :post, :only => [:create], :redirect_to => {:action => :create}
  def index
    # do not delete
  end
  
  def success
    # do not delete
  end
  
  def trophyList
   # do not delete
  end
  
  def cupList
   # do not delete
  end
  
  def create 
    @trophy = Trophy.new(params[:trophy])
    
    returnvalue = @trophy.saveTrophy()
    if ( returnvalue == Trophy::CRITERIA_CREATED)
      redirect_to :controller => "trophies", :action => "success"
    elsif ( returnvalue == Trophy::CRITERIA_ALREADY_EXISTS) 
      flash[:repeatnotice] = "That criteria already exists!"
      redirect_to :controller => "trophies", :action => "index"
    else
      redirect_to :controller => "trophies", :action => "index"
      
    end
  
  end 
  
  def addCriteria
    respond_to do |format|        
      
      format.js   { render :js => "$('#add_criteria_id').fadeOut()" }
    end
  end
 

end
