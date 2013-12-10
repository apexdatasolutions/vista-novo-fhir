var mongoose = require('mongoose');
var _ = require('underscore');
var fs = require('fs');
var eco = require('eco');
var async = require('async');
var <ModelName> = mongoose.model('<ModelName>');
var ResourceHistory = mongoose.model('ResourceHistory');
var ResponseFormatHelper = require(__dirname + '/../../lib/response_format_helper');

exports.load = function(req, res, id, vid, next) {
  if (req.resourceHistory) {
    req.resourceHistory.findLatest(function(err, <LowerCaseModelName>) {
      req.<LowerCaseModelName> = <LowerCaseModelName>;
      next(<LowerCaseModelName>);
    });
  } else {
    ResourceHistory.findOne(id, function(rhErr, resourceHistory) {
      if (rhErr) {
        next(rhErr);
      }
      if(resourceHistory !== null) {
        req.resourceHistory = resourceHistory;
        req.resourceHistory.findLatest(function(err, <LowerCaseModelName>) {
          req.<LowerCaseModelName> = <LowerCaseModelName>;
          next(<LowerCaseModelName>);
        });
      }
    });
  }
};

exports.show = function(req, res) {
  var <LowerCaseModelName> = req.<LowerCaseModelName>;
  var json = JSON.stringify(<LowerCaseModelName>);
  res.send(json);
};

exports.create = function(req, res) {
  var <LowerCaseModelName> = new <ModelName>(req.body);
  <LowerCaseModelName>.save(function(err, saved<ModelName>) {
    if(err) {
      res.send(500);
    } else {
      var resourceHistory = new ResourceHistory({resourceType: '<ModelName>'});
      resourceHistory.addVersion(saved<ModelName>.id);
      resourceHistory.save(function(rhErr, savedResourceHistory){
        if (rhErr) {
          res.send(500);
        } else {
          res.set('Location', ("http://localhost:3000/<LowerCaseModelName>/@" + resourceHistory.id));
          res.send(201);
        }
      });
    }
  });
};

exports.update = function(req, res) {
  var <LowerCaseModelName> = req.<LowerCaseModelName>;
  <LowerCaseModelName> = _.extend(<LowerCaseModelName>, req.body);
  <LowerCaseModelName>.save(function(err, saved<LowerCaseModelName>) {
    if(err) {
      res.send(500);
    } else {
      var resourceHistory = req.resourceHistory;
      resourceHistory.addVersion(saved<LowerCaseModelName>);
      resourceHistory.save(function(rhErr, savedResourceHistory) {
        if (rhErr) {
          res.send(500);
        } else {
          res.send(200);
        }
      });
    }
  });
};

exports.destroy = function(req, res) {
  var <LowerCaseModelName> = req.<LowerCaseModelName>;
  <LowerCaseModelName>.remove(function (err) {
    if(err) {
      res.send(500);
    } else {
      res.send(204);
    }
  });
};

exports.list = function(req, res) {
  var models = [];
  var template = fs.readFileSync(__dirname + "/../views/atom.xml.eco", "utf-8");

  ResourceHistory.find({resourceType:"<ModelName>"}, function (rhErr, histories) {
    if (rhErr) {
      return next(rhErr);
    }
    if (histories !== null) {
      async.forEach(histories, function(history, callback) {
        history.findLatest( function(err, <LowerCaseModelName>) {
          models.push(<LowerCaseModelName>);
          callback();
        });
      }, function(err) {
          console.log(models);
          res.send(eco.render(template, models));
      });
    } else {
      console.log('no <LowerCaseModelName> found');
      res.send(500);
    }
  });
};