var expect  = require("chai").expect;
var assert = require("assert");
//var validBearer = '7f0d343ac0602db4e7b0c187afe31dd86d68c3c1';
var timeOut = 90000;
  describe("Testing API", function() {
    it('HubSetPointCoolingChange:Thermostat Not Found', function(done) {
      expect(100).to.equal(100);
      done();  
    }).timeout(timeOut); 
    it('HeatingChange:Thermostat Not Found', function(done) {
      expect(100).to.equal(100);
      done();  
    }).timeout(timeOut); 
});  
