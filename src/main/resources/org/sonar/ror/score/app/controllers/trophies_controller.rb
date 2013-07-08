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
  
  def create 
    @trophy = Trophy.new(params[:trophy])
    @trophyType = @trophy.get_type()
    if @trophy.saveTrophy() 
      redirect_to :controller => "trophies", :action => "success"
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
